package win.leizhang.demo.captcha.api.dto.base;


public class AbstractPublicInputDTO extends AbstractBaseDTO {

    /**
     * 请求方穿过来的transactionUuid ，目的在于系统查看日志的是根据这个transactionUuid可以快速定位所有的日志
     */
    private String transactionUuid;

    /**
     * 请求方系统编码
     */
    protected String sysId;

    /**
     * 渠道编码
     */
    protected String channelId;

    /**
     * 事件类型，mq用的
     */
    protected String eventType;

    public String getTransactionUuid() {
        return transactionUuid;
    }

    public void setTransactionUuid(String transactionUuid) {
        this.transactionUuid = transactionUuid;
    }

    public String getSysId() {
        return sysId;
    }

    public void setSysId(String sysId) {
        this.sysId = sysId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}
