$(function () {
    var productListUrl='/o2o/shopadmin/getproductlist';
    var editProductUrl ='/o2o/shopadmin/productoperation';
    var productOffShelvesUrl = '/o2o/shopadmin/productoffshelves';
    var productPreviewUrl = '/o2o/shopadmin/productpreview';
    var addProductUrl='/o2o/shopadmin/productoperation'
    getProductList();
    function getProductList() {
        $.getJSON(
            productListUrl,
            function (data) {
                if(data.success){
                    var dataList = data.data;
                    $('.product-wrap').html("");
                    var tempHtml='';
                    dataList.map(function (item,index){
                        tempHtml+=''
                            +'<div class="row row-product now">'
                            +'<div class="col-33 product-name"> '
                            +item.productName
                            +'</div>'
                            +'<div class="col-20">'
                            +item.priority
                            +'</div>'
                            +'<div class="col-45">'
                              +'<a href="/o2o/shopadmin/productoperation?productId='
                              +item.productId
                              +'"> 编辑</a>&nbsp;'
                              +'<a href="#" class="product-off-Shelves" data-id="'
                              +item.productId
                              +'"> 下架</a>&nbsp;'
                              +'<a href="#" class="product-preview" data-id="'
                              +item.productId
                              +'"> 预览</a>'
                            + '</div>'
                            +'</div>'
                    });
                    $('.product-wrap').append(tempHtml);
                }
            });
    }

    $('.product-wrap').on('click','.product-edit',
        function (e) {
            var target = e.currentTarget;
                $.ajax({
                    url:editProductUrl,
                    type:'POST',
                    data:{
                        productId:target.dataset.id
                    },
                    dataType:'json',
                    // success:function (data) {
                    //     if(data.success){
                    //         $.toast('删除成功！');
                    //         getProductList();
                    //     }else {
                    //         $.toast('删除失败！');
                    //     }
                    // }
                });

        });


});