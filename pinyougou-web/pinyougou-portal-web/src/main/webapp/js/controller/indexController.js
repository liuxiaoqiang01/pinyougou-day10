/** 定义首页控制器层 */
app.controller("indexController", function($scope, baseService){

    /** 根据广告id 查询广告内容*/
    $scope.findContentByCategoryId = function (categoryId) {
        baseService.sendGet("/findContentByCategoryId?categoryId="
            + categoryId).then(function (response) {
                $scope.dataList = response.data;
        })

    }

});