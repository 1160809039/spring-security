package xin.shaozeming.security.common.Enum;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 邵泽铭
 * \* Date: 2018/5/12
 * \* Time: 18:42
 * \* Description:
 * \ 返回值状态码
 */

public enum State {

    RES_SUCCES((byte) 0,"成功"),
    RES_EMPTY((byte) 1,"没有数据"),
    RES_INTERNET((byte) 2,"网络错误"),
    RES_NOUSER((byte) 3,"用户名不存在"),
    RES_NOUSERORPASS((byte) 4,"用户名不存在或密码错误"),
    RES_NOPERMIT((byte) 5,"权限不足"),
    RES_NOLOGIN((byte) 6,"尚未登录"),
    RES_PARAMERROR((byte) 7,"参数异常"),
    RES_REQERROR((byte) 8,"非法请求");

    private byte code;
    private String value;
    private State(byte code,String value){
        this.code=code;
        this.value=value;
    }
   public byte getCode(){
        return this.code;
   }
    public String getvalue(){
        return this.value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
