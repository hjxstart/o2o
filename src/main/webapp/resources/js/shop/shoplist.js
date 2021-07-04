/**
 * 商品列表模块
 */
$(function () {
    getlist();
    function getlist(e) {
        $.ajax({
            url: "/o2o/shopadmin/getshoplist",
            type: "get",
            dataType: "json",
            success: function (data) {
                if (data.success) {
                    handleList(data.shopList);
                    handleUser(data.user);
                }
            }
        });
    }

    /**
     * 显示用户名
     */
    function handleUser(data) {
        $('#user-name').text(data.name);
    }

    /**
     * 显示列表信息
     * @param {*} data 
     */
    function handleList(data) {
        var html = '';
        data.map(function (item, index) {
            html += '<div class="row row-shop"><div class="col-40">' + item.shopName + '</div><div class="col-40">' + shopStatus(item.enableStatus) + '</div><div class="col-20">' + goShop(item.enableStatus, item.shopId) + '</div></div>';

        });
        $('.shop-wrap').html(html);
    }

    /**
     * 生成连接，方便进入详细信息
     * @param {*} status 
     * @param {*} id 
     * @returns 
     */
    function goShop(status, id) {
        if (status != 0 && status != -1) {
            return '<a href="/o2o/shopadmin/shopmanagement?shopId=' + id + '">进入</a>';
        } else {
            return '';
        }
    }

    /**
     * 显示后台的shopStatus
     * @param {*} status 
     * @returns 
     */
    function shopStatus(status) {
        if (status == 0) {
            return '审核中';
        } else if (status == -1) {
            return '店铺非法';
        } else {
            return '审核通过';
        }
    }
})