<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>VuiMarket</title>
        <link rel="stylesheet" type="text/css" href="css/confirm.css">
        <style>
            .error-message {
                color: red;
                background-color: #ffebee;
                padding: 10px 20px;
                border-radius: 5px;
                box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);
                margin-bottom: 20px;
            }
        </style>
    </head>
    <body class="body-container">

        <div class="auth-container">
            <img src="img/logo.png" alt="Logo" class="logo">
            <h2>Quên Mật khẩu</h2>

            <form action="forgotPassword" method="post">
                <div class="input-group">
                    <input type="text" name="username" id="username" placeholder="Enter your username" required>
                </div>
                <div>
                    <button><a href="login">Back</a></button>
                    <button type="submit" class="activate-button">Yêu cầu cấp lại mật khẩu</button>
                </div>
            </form>

            <%
                String error = request.getParameter("error");
                if (error != null && error.equals("notfound")) {
            %>
            <div class="error-message">Không tìm thấy tài khoản!</div>
            <% }%>
        </div>

    </body>
</html>
