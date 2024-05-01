<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="entity.User"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>VuiMarket</title>
        <link rel="stylesheet" href="css/profile.css">

        <script>
            function togglePasswordVisibility() {
                var oldPassword = document.getElementById("oldPassword");
                var newPassword = document.getElementById("newPassword");
                var confirmNewPassword = document.getElementById("confirmNewPassword");
                if (oldPassword.type === "password") {
                    oldPassword.type = "text";
                    newPassword.type = "text";
                    confirmNewPassword.type = "text";
                } else {
                    oldPassword.type = "password";
                    newPassword.type = "password";
                    confirmNewPassword.type = "password";
                }
            }
        </script>
    </head>
    <body>
        <div class="password-change-container">
            <h2>Thay Đổi Mật Khẩu</h2>
            <% 
                User account = (User) request.getSession().getAttribute("acc");
                if (account == null) {
                    response.sendRedirect("login.jsp"); // Hoặc trang thông báo lỗi nếu cần
                    return;
                }

                if (request.getAttribute("message") != null) {
            %>
            <p class="notification"><%= request.getAttribute("message") %></p>
            <% } %>
            <form action="changepass" method="POST">
                <input type="hidden" id="accountId" name="accountId" value="<%= account.getUid() %>">
                <div class="form-group">
                    <label for="oldPassword">Mật khẩu cũ:</label>
                    <input type="password" id="oldPassword" name="oldPassword" required>
                </div>
                <div class="form-group">
                    <label for="newPassword">Mật khẩu mới:</label>
                    <input type="password" id="newPassword" name="newPassword" required>
                </div>
                <div class="form-group">
                    <label for="confirmNewPassword">Xác nhận mật khẩu mới:</label>
                    <input type="password" id="confirmNewPassword" name="confirmNewPassword" required>
                </div>
                <div class="form-group">
                    <input type="checkbox" id="showPassword" onclick="togglePasswordVisibility()">
                    <label for="showPassword" class="show-password">Hiển thị mật khẩu</label>
                </div>
                <button type="submit" class="update-button">Đổi Mật Khẩu</button>
                <a href="home">Trở về trang chủ</a>
            </form>
        </div>
    </body>
</html>
