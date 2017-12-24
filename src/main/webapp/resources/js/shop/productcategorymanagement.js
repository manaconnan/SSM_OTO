$(function () {
    var listUrl ='/o2o/shopadmin/getproductcategorylist';
    var addUrl = '/o2o/shopadmin/addproductcategories';
    var deleteUrl = '/o2o/shopadmin/removeproductcategory';
    getProductCategoryList();
    function getProductCategoryList() {
        $.getJSON(
            listUrl,
            function (data) {
                if(data.success){
                    var dataList = data.data;
                    $('.product-category-wrap').html("");
                    var tempHtml='';
                    dataList.map(function (item,index){
                        tempHtml+=''
                                +'<div class="row row-product-category now">'
                                +'<div class="col-33 product-category-name"> '
                                +item.productCategoryName
                                +'</div>'
                                +'<div class="col-33"> '
                                +item.priority
                                +'</div>'
                                +'<div class="col-33"><a href="#" class="button delete" data-id="'
                                +item.productCategoryId
                                +'"> 删除</a></div>'
                                +'</div>'
                    });
                    $('.product-category-wrap').append(tempHtml);
                }
            });
    }

    $('#add-proCategory')
        .click(
                function () {
                    var tempHtml = '<div class="row row-product-category temp">'
                            +'<div class="col-33"><input class="category_input category" type="text" placeholder="商品类别"></div> '
                            +'<div class="col-33"><input class="category_input priority" type="text" placeholder="优先级"></div> '
                            +'<div class="col-33"><a href="#" class="button delete" >删除</a></div> '
                            +'</div>'
                    $('.product-category-wrap').append(tempHtml);
                }
    );
    $('#sub-proCategory').click(
        function () {
            var tempArr = $('.temp');
            var productCategoryList=[];
            tempArr.map(function (index,item) {
                var tempObj={};
                tempObj.productCategoryName=$(item).find('.category').val();
                tempObj.priority=$(item).find('.priority').val();
                if(tempObj.productCategoryName&&tempObj.priority){
                    productCategoryList.push(tempObj);
                }
            });
            $.ajax({
                url:addUrl,
                type:'POST',
                data: JSON.stringify(productCategoryList),
                contentType:'application/json',
                success:function (data) {
                    if(data.success){
                        $.toast('提交成功');
                        getProductCategoryList();
                    }else {
                        $.toast('提交失败');
                    }
                }
            });
        });
    $('.product-category-wrap').on('click','.row-product-category.temp .delete',
            function () {
                console.log($(this).parent().parent());
                $(this).parent().parent().remove();
             }
        );
    $('.product-category-wrap').on('click','.row-product-category.now .delete',
        function (e) {
            var target = e.currentTarget;
            $.confirm('确定删除？',function () {
                $.ajax({
                    url:deleteUrl,
                    type:'POST',
                    data:{
                        productCategoryId:target.dataset.id
                    },
                    dataType:'json',
                    success:function (data) {
                        if(data.success){
                            $.toast('删除成功！');
                            getProductCategoryList();
                        }else {
                            $.toast('删除失败！');
                        }
                    }
                });
            });

    });


});