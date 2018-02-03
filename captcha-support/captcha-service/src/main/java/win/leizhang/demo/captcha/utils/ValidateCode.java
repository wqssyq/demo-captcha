package win.leizhang.demo.captcha.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * 验证码生成器
 *
 * @author zhanglei863
 *         {@link} http://blog.csdn.net/zhulin40/article/details/51899487
 */
public class ValidateCode {
	// 图片的宽度。
	private int width = 160;
	// 图片的高度。
	private int height = 40;
	// 验证码字符个数
	private int codeCount = 5;
	// 验证码干扰线数
	private int lineCount = 150;
	// 验证码
	private String code = null;
	// 验证码图片Buffer
	private BufferedImage buffImg = null;

	// 验证码范围,去掉0(数字)和O(拼音)容易混淆的(小写的1和L也可以去掉,大写不用了)
	private char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R',
			'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '1', '2', '3', '4', '5', '6', '7', '8', '9',
	};

	/*
		'销', '售', '第', '一', '自', '力', '更', '生', '解', '放', '思', '想', '勇', '当', '战', '士',
		'开', '放', '民', '主', '平', '等', '新', '颖',
		'华', '润', '通', '深', '圳', '湾', '中', '国',
	 */

	/**
	 * 默认构造函数,设置默认参数
	 */
	public ValidateCode() {
		this.createCode();
	}

	/**
	 * @param width
	 *            图片宽
	 * @param height
	 *            图片高
	 */
	public ValidateCode(int width, int height) {
		this.width = width;
		this.height = height;
		this.createCode();
	}

	/**
	 * @param width
	 *            图片宽
	 * @param height
	 *            图片高
	 * @param codeCount
	 *            字符个数
	 * @param lineCount
	 *            干扰线条数
	 */
	public ValidateCode(int width, int height, int codeCount, int lineCount) {
		this.width = width;
		this.height = height;
		this.codeCount = codeCount;
		this.lineCount = lineCount;
		this.createCode();
	}

	public void createCode() {
		int x = 0, fontHeight = 0, codeY = 0;
		int red = 0, green = 0, blue = 0;

		x = width / (codeCount + 2);// 每个字符的宽度(左右各空出一个字符)
		fontHeight = height - 2;// 字体的高度
		codeY = height - 4;

		// 图像buffer
		buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = buffImg.createGraphics();
		// 生成随机数
		Random random = new Random();
		// 将图像填充为白色
		//g.setColor(Color.WHITE);
		g.setBackground(Color.gray);//设置背景色
		g.fillRect(0, 0, width, height);
		// 创建字体,可以修改为其它的
		Font font = getFont(fontHeight);
		g.setFont(font);

		for (int i = 0; i < lineCount; i++) {
			// 设置随机开始和结束坐标
			int xs = random.nextInt(width);// x坐标开始
			int ys = random.nextInt(height);// y坐标开始
			int xe = xs + random.nextInt(width / 8);// x坐标结束
			int ye = ys + random.nextInt(height / 8);// y坐标结束

			// 产生随机的颜色值，让输出的每个干扰线的颜色值都将不同。
			red = random.nextInt(255);
			green = random.nextInt(255);
			blue = random.nextInt(255);
			g.setColor(new Color(red, green, blue));
			g.drawLine(xs, ys, xe, ye);
		}

		// randomCode记录随机产生的验证码
		StringBuffer randomCode = new StringBuffer();
		// 随机产生codeCount个字符的验证码。
		for (int i = 0; i < codeCount; i++) {
			String strRand = String.valueOf(codeSequence[random.nextInt(codeSequence.length)]);
			// 产生随机的颜色值，让输出的每个字符的颜色值都将不同。
			red = random.nextInt(255);
			green = random.nextInt(255);
			blue = random.nextInt(255);
			g.setColor(new Color(red, green, blue, 200));// alpha=140,辨别度很低了
			g.drawString(strRand, (i + 1) * x, codeY);
			// 将产生的四个随机数组合在一起。
			randomCode.append(strRand);
		}
		// 将四位数字的验证码保存到Session中。
		code = randomCode.toString();
	}

	public void write(String path) throws IOException {
		OutputStream sos = new FileOutputStream(path);
		this.write(sos);
	}

	public void write(OutputStream sos) throws IOException {
		ImageIO.write(buffImg, "png", sos);
		sos.close();
	}

	public BufferedImage getBuffImg() {
		return buffImg;
	}

	public String getCode() {
		return code;
	}

	/**
	 * 产生随机字体
	 */
	private Font getFont(int size) {
		Random random = new Random();
		Font font[] = new Font[6];
		font[0] = new Font("Ravie", Font.PLAIN, size);
		font[1] = new Font("Antique Olive Compact", Font.PLAIN, size);
		font[2] = new Font("Fixedsys", Font.BOLD, size);
		font[3] = new Font("Wide Latin", Font.PLAIN, size);
		font[4] = new Font("Gill Sans Ultra Bold", Font.PLAIN, size);
		font[5] = new Font("Times New Roman", Font.ROMAN_BASELINE, size);
		return font[random.nextInt(6)];
	}

	/**
	 * 测试函数,默认生成到d盘
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ValidateCode vCode = new ValidateCode(180, 60, 4, 200);
		try {
			//String path = "D:/" + new Date().getTime() + ".png";
			String path = "/Volumes/FAV/test/" + System.currentTimeMillis() + ".png";
			System.out.println(vCode.getCode() + " >" + path);
			vCode.write(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
