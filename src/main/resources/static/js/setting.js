$(function(){
    $("#uploadForm").submit(upload);
});

function upload(){
    $.ajax({
        url: "http://up-z2.qiniup.com",
        method: "post",
        processData: false,
        contentType: false,
        data: new FormData( $("#uploadForm")[0]),
        success: function(data){
            console.log("上传响应数据:", data);
            if(data && data.code == 0){
            //更新头像访问路径
            $.post(
                CONTEXT_PATH + "/user/header/url",
                {"fileName":$("input[name='key']").val()},
                function(data){
                    data = $.parseJSON(data);
                     console.log("更新头像路径响应数据:", data);
                    if(data.code == 0){
                        window.location.reload();
                    }else{
                    alert(data.msg);
                    }
                }
            );
            }else{
            alert("上传失败");
            }
        },
        error: function(error) {
                    console.error("上传请求出错:", error);
        }
    });
    return false;
}