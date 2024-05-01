<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>Sign In</title>

        <!-- Font Icon -->
        <link rel="stylesheet"
              href="fonts/material-icon/css/material-design-iconic-font.min.css">
        <!-- Main css -->
        <link rel="stylesheet" href="css/style1.css">

    </head>
    <body>

        <div class="main">

            <!-- Sing in  Form -->
            <section class="sign-in">
                <div class="container">
                    <div class="signin-content">
                        <div class="signin-image">
                            <figure>
                                <img src="https://i.pinimg.com/564x/24/7f/e5/247fe5988b99aeb3ee2b7caf178e7aaf.jpg" alt="sing up image">
                            </figure>
                            <a href="registration.jsp" class="signup-image-link">Create an
                                account</a>
                        </div>

                        <div class="signin-form">
                            <h2 class="form-title">Sign In</h2>
                            <form method="post" action="login" class="register-form"
                                  id="login-form">
                                <div class="form-group">
                                    <label for="username"><i
                                            class="zmdi zmdi-account material-icons-name"></i></label> <input
                                        type="text" name="user" id="username"
                                        placeholder="Your Name" />
                                </div>
                                <div class="form-group">
                                    <label for="password"><i class="zmdi zmdi-lock"></i></label> <input
                                        type="password" name="pass" id="password"
                                        placeholder="Password" />
                                </div>

                                <div>
                                    <input style="display: none" type="text" name="capchaId" id="capchaIdInput" value="" />
                                    <img id="captchaImage" src="" alt="" />
                                </div>

                                <div>
                                    Enter capcha: <input type="text" name="capcha" value="" />
                                </div>
                                <div class="form-group form-button">
                                    <input type="submit" name="signin" id="signin"
                                           class="form-submit" value="Log in" />
                                    <button type="button" id="refreshCaptcha">Refresh Captcha</button>
                                </div>
                                <div>${mess}</div>
                                <div class="form-group">
                                    <a href="forgotPassword">Forgot Password?</a>
                                </div>

                            </form>

                            <div class="error-message">
                                <c:if test="${not empty errorMessage}">
                                    <div class="alert alert-danger" role="alert">
                                        ${errorMessage}
                                    </div>
                                </c:if>
                            </div>

                        </div>
                    </div>
                </div>
            </section>

        </div>

        <!-- JS -->
        <script src="vendor/jquery/jquery.min.js"></script>
        <script src="js/main.js"></script>
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
    <!-- This templates was made by Colorlib (https://colorlib.com) -->
</html>