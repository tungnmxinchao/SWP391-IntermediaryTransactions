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

            <h2>Xác thực Tài khoản "${username}"</h2>

            <!-- Form xác thực tài khoản -->
            <form action="confirm" method="post">
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
                <div>${mess}</div>

                <input type="hidden" name="code" value="${codeConfirm}">
                <!--Nút kích hoạt tài khoản-->
                <button type="submit" class="activate-button">Kích hoạt tài khoản</button>
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

        </script>
    </body>
</html>
