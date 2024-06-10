const acceptOrder = (orderId)=>{
        let url = `/store-order/id/${orderId}`

        $.ajax({
            url      : url,
            type     : "POST",
            contentType : "application/json",
            dataType : "json",
            cache   : false,
            success  : function(result, status){
                alert("주문 수락");
                location.reload();
            },
            error : function(jqXHR, status, error){
                if(jqXHR.status == '401'){
                    alert('로그인 후 이용해주세요');
                    location.href='/store-user/login';
                } else{
                    alert(jqXHR.responseJSON.message);
                }
            }
        });
 }

 const deleteOrder = (orderId)=>{
     let url = `/store-order?id=${orderId}` ;

     $.ajax({
         url      : url,
         type     : "POST",
         contentType : "application/json",
         dataType : "json",
         cache   : false,
         success  : function(result, status){
             alert("주문 취소");
             location.reload();
         },
         error : function(jqXHR, status, error){
             if(jqXHR.status == '401'){
                 alert('로그인 후 이용해주세요');
                 location.href='/store-user/login';
             } else{
                 alert(jqXHR.responseJSON.message);
             }
         }
     });
 }