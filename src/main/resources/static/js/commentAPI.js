let host = "http://localhost:8888/rest/comment";
const app = angular.module("app", ["ngRoute"]);

app.config(['$locationProvider', function ($locationProvider) {
    $locationProvider.hashPrefix('');
}]);
app.controller("comment-ctrl", function ($scope, $http, $location, $window) {
    var $comment = ($scope.comment = {
        // Hàm để tải danh sách bình luận từ server
        loadComments() {
            $http.get(host + '/product/{productId}')
                .then(function (response) {
                    $scope.comments = response.data;
                })
                .catch(function (error) {
                    console.error('Lỗi khi tải danh sách bình luận:', error);
                });
        }
        ,


        // Hàm để thêm bình luận mới
        addComment() {
            var commentContent = $scope.newCommentContent;

            $http.post(host + '/comments', {content: commentContent})
                .then(function (response) {
                    // Sau khi thành công, cập nhật danh sách bình luận
                    loadComments();
                    // Xóa nội dung trong form
                    $scope.newCommentContent = '';
                })
                .catch(function (error) {
                    console.error('Lỗi khi thêm bình luận:', error);
                });
        },

    });
    $comment.loadComments();
});
