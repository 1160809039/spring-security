package xin.shaozeming.security.common;

import lombok.Data;
import xin.shaozeming.security.common.Enum.State;


/**
 * \* Created with IntelliJ IDEA.
 * \* User: 邵泽铭
 * \* Date: 2018/5/27
 * \* Time: 17:25
 * \* Description:
 * \
 */

@Data
public class Response<T> {
    /** 状态 0 正常 1 没有数据 2 网络异常  */
    private byte status;


    /**返回信息*/
    private String msg;

    /**数据*/
    private  T data;

    public Response(T data){
         this.data=data;
    }
    public Response(){}



    /**根据不同的status，返回不同的结果*/
    public Response(byte status){
        this.status=status;
        for(State state: State.values()){
            if(status==state.getCode()){
                this.msg=state.getvalue();
            }
        }
    }


}