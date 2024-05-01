<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>VuiMarket</title>
        <link rel="stylesheet" type="text/css" href="css/confirm.css">
        
    </head>

    <body class="body-container">

        <div class="auth-container">
            <img src="img/logo.png" alt="Logo" class="logo">

            <h2>Đặt lại mật khẩu "${username}"</h2>

            <form action="passwordRecovery" method="post">
                <div class="form-group">
                    <label for="newPassword">Mật khẩu mới</label>
                    <input type="password" name="newPassword" id="newPassword" required>
                </div>

                <div class="form-group">
                    <label for="confirmPassword">Xác nhận mật khẩu mới</label>
                    <input type="password" name="confirmPassword" id="confirmPassword" required>
                </div>

                <div class="form-group">
                    <input type="checkbox" id="showPassword" onclick="togglePasswordVisibility()">
                    <label for="showPassword" class="show-password">Hiển thị mật khẩu</label>
                </div>

                <div>
                    <input style="display: none" type="text" name="capchaId" id="capchaIdInput" value="" />
                    <img id="captchaImage" src="" alt="">
                </div>

                <div>
                    Enter capcha: <input type="text" name="capcha" value="" />
                </div>
                <div class="form-group form-button">
                    <button type="button" class="refresh-button" id="refreshCaptcha">Refresh Captcha</button>
                </div>

                <input type="hidden" name="code" value="${codeConfirm}">

                <div style="color: red">${mess}</div>

                <button type="submit" class="activate-button">Đổi mật khẩu</button>
            </form>
        </div>

        <script>
            function fetchAndSetCaptcha() {
                fetch('generateCapcha')
                        .then(response => response.json())
                        .then(data => {
                            document.getElementById('captchaImage').src = 'data:image/png;base64,' + data.bufferedImage;
                            document.getElementById('capchaIdInput').value = data.idCapcha;
                        })
                        .catch(error => console.error('Error:', error));
            }

            // Initial load
            fetchAndSetCaptcha();

            function refreshCaptcha() {
                fetchAndSetCaptcha();
            }

            document.getElementById('refreshCaptcha').addEventListener('click', refreshCaptcha);

            function togglePasswordVisibility() {
                var newPassword = document.getElementById("newPassword");
                var confirmNewPassword = document.getElementById("confirmPassword");
                if (newPassword.type === "password") {
                    newPassword.type = "text";
                    confirmNewPassword.type = "text";
                } else {
                    newPassword.type = "password";
                    confirmNewPassword.type = "password";
                }
            }
        </script>
    </body>

</html>
