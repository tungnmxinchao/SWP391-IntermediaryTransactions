<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>Sign Up</title>

        <!-- Font Icon -->
        <link rel="stylesheet" href="fonts/material-icon/css/material-design-iconic-font.min.css">

        <!-- Main css -->
        <link rel="stylesheet" href="css/style1.css">
        <style>
            .error-message {
                color: red;
                font-size: 20px;
                margin-top: -10px;
            }

        </style>
    </head>
    <body>

        <div class="main">
            <!-- Sign up form -->
            <section class="signup">
                <div class="container">
                    <div class="signup-content">
                        <div class="signup-form">
                           
                            <%
                                String error = request.getParameter("error");
                            %>
                            <% if (error != null) { %>
                            <% if (error.equals("1")) { %>
                            <p class="error-message">Tài khoản đã tồn tại, Vui lòng tạo tài khoản khác</p>
                            <% } else if (error.equals("2")) { %>
                            <p class="error-message">Mật khẩu không khớp, Vui lòng kiểm tra lại!</p>
                            <% } else if (error.equals("3")) { %>
                            <p class="error-message">Vui lòng nhập lại số điện thoại có từ 10 hoặc 11 số!</p>
                            <% } %>
                            <% }%>

                            <h2 class="form-title">Sign up</h2>
                            <form method="post" action="register" class="register-form" id="register-form" accept-charset="UTF-8">
                                <div class="form-group">
                                    <label for="name"><i class="zmdi zmdi-account material-icons-name"></i></label>
                                    <input type="text" name="name" id="name" placeholder="Your Name" />
                                </div>
                                <div class="form-group">
                                    <label for="email"><i class="zmdi zmdi-email"></i></label>
                                    <input type="email" name="email" id="email" placeholder="Your Email" />
                                </div>
                                <div class="form-group">
                                    <label for="pass"><i class="zmdi zmdi-lock"></i></label>
                                    <input type="password" name="pass" id="pass" placeholder="Password" />
                                    <span class="toggle-password" onclick="togglePasswordVisibility('pass')">
                                        <i class="zmdi zmdi-eye"></i>
                                    </span>
                                </div>
                                <div class="form-group">
                                    <label for="re-pass"><i class="zmdi zmdi-lock-outline"></i></label>
                                    <input type="password" name="re_pass" id="re_pass" placeholder="Repeat your password" />
                                    <span class="toggle-password" onclick="togglePasswordVisibility('re_pass')">
                                        <i class="zmdi zmdi-eye"></i>
                                    </span>
                                </div>
                                <div class="form-group">
                                    <label for="contact"><i class="zmdi zmdi-lock-outline"></i></label>
                                    <input type="text" name="contact" id="contact" placeholder="Contact no" />
                                </div>
                                <div class="form-group form-button">
                                    <input type="submit" name="signup" id="signup" class="form-submit" value="Register" />
                                </div>
                            </form>
                        </div>
                        <div class="signup-image">
                            <figure>
                                <img src="images/signup-image.jpg" alt="sing up image">
                            </figure>
                            <a href="Login.jsp" class="signup-image-link">I am already member</a>
                        </div>
                    </div>
                </div>
            </section>
        </div>

        <!-- JS -->
        <script src="vendor/jquery/jquery.min.js"></script>
        <script src="js/main.js"></script>
        <script>
                                        function togglePasswordVisibility(fieldId) {
                                            var passwordInput = document.getElementById(fieldId);
                                            var fieldType = passwordInput.getAttribute('type');

                                            if (fieldType === 'password') {
                                                passwordInput.setAttribute('type', 'text');
                                            } else {
                                                passwordInput.setAttribute('type', 'password');
                                            }
                                        }
        </script>

    </body>
</html>
