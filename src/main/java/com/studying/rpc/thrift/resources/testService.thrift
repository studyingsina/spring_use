namespace java com.studying.rpc.thrift.stub.service

include "thrift_datatype.thrift"

service TestThriftService
{

    /**
    *value 中存放两个字符串拼接之后的字符串
    */
    thrift_datatype.ResultStr getStr(1:string srcStr1, 2:string srcStr2),

    thrift_datatype.ResultInt getInt(1:i32 val)

}