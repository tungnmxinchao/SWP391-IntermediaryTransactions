<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="entity.User"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>User Profile</title>
        <link rel="stylesheet" href="css/profile.css">
    </head>
    <body>
        <div class="user-profile-container">
            <h2>Thông tin cá nhân</h2>
            <% if (request.getAttribute("message") != null) {%>
            <p class="notification"><%= request.getAttribute("message")%></p>
            <% }%>
            <form action="updateprofile" method="POST">
                <div class="form-group">
                    <label for="accountId">Mã tài khoản:</label>
                    <input type="text" id="accountId" name="accountId" value="<%= ((User) request.getAttribute("account")).getUid()%>" readonly>
                </div>
                <div class="form-group">
                    <label for="fullName">Họ tên:</label>
                    <input type="text" id="fullName" name="fullName" value="<%= ((User) request.getAttribute("account")).getFullname()%>">
                </div>
                <div class="form-group">
                    <label for="phone">Số điện thoại:</label>
                    <input type="text" id="phone" name="phone" value="<%= ((User) request.getAttribute("account")).getPhone()%>">
                </div>
                <div class="form-group">
                    <label for="email">Email(*):</label>
                    <input type="email" id="email" name="email" value="<%= ((User) request.getAttribute("account")).getEmail()%>" readonly>
                </div>
                <div class="form-group">
                    <label for="detail">Mô tả:</label>
                    <textarea id="detail" name="detail" placeholder="Giới thiệu bản thân"><%= ((User) request.getAttribute("account")).getDetail()%></textarea>
                </div>
                <button class="update-button" type="submit">Cập nhật</button>
                <a href="home">Back to Home</a>
            </form>

            <a href="ChangePass.jsp" class="change-password-button">Đổi mật khẩu</a>
        </div>
    </body>
</html>
