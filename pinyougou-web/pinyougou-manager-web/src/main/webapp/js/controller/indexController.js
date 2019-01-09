app.controller('indexController',function ($scope,baseService) {
    // 获取登录用户名
    $scope.showLoginName = function () {
        baseService.sendGet("/showLoginName")
            .then(function (response) {

            // 获取登录用户名 响应数据
            $scope.loginName = response.data.loginName;

        })
    }
    
    
})