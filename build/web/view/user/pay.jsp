<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.*" %>
<%@page  import = "java.util.ArrayList "%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <!-- Basic -->
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <!-- Mobile Metas -->
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <!-- Site Metas -->
        <meta name="keywords" content="" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <link rel="shortcut icon" href="images/favicon.png" type="">

        <title> ShopCar </title>

        <!-- bootstrap core css -->
        <link rel="stylesheet" type="text/css" href="css/bootstrap.css" />

        <!--owl slider stylesheet -->
        <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/assets/owl.carousel.min.css" />
        <!-- nice select  -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-nice-select/1.1.0/css/nice-select.min.css" integrity="sha512-CruCP+TD3yXzlvvijET8wV5WxxEh5H8P4cmz0RFbKK6FlZ2sYl3AEsKlLPHbniXKSrDdFewhbmBK5skbdsASbQ==" crossorigin="anonymous" />
        <!-- font awesome style -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" integrity="sha512-DTOQO9RWCH3ppGqcWaEA1BIZOC6xxalwEsw9c2QQeAIftl+Vegovlnee1c9QX4TctnWMn13TZye+giMm8e2LwA==" crossorigin="anonymous" referrerpolicy="no-referrer" />

        <!-- Custom styles for this template -->
        <link href="css/style1.css" rel="stylesheet" type="text/css"/>
        <!-- responsive style -->
        <link href="css/responsive.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <link rel="stylesheet" href="./css/user_1.css"/>

    </head>

    <body class="sub_page">

        <div class="hero_area">
           <div class="bg-box">
                <img src="images/backgroundGrey.jpg" alt="">
            </div>
            <!-- header section strats -->
            <header class="header_section">
                <div class="container">
                    <nav class="navbar navbar-expand-lg custom_nav-container ">
                        <a class="navbar-brand" href="home">
                            <span>
                                ShopCar
                            </span>
                        </a>

                        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                            <span class=""> </span>
                        </button>

                        <div class="collapse navbar-collapse" id="navbarSupportedContent">
                            <ul class="navbar-nav  mx-auto ">
                                <li class="nav-item">
                                    <a class="nav-link" href="home">Home </a>
                                </li>
                                <li class="nav-item active">
                                    <a class="nav-link" href="menu">Menu <span class="sr-only">(current)</span> </a>
                                </li>

                            </ul>
                            <div class="user_option">
                                <c:if test="${sessionScope.user == null}">
                                    <button class="btn btn-dark"><a style="text-decoration: none;color: white" href="login">Login</a></button>
                                    <button class="btn btn-dark mx-3"><a style="text-decoration: none;color: white" href="signup">Sign-up</a></button>

                                </c:if>

                                 <c:if test="${sessionScope.user != null}">
                                    <ul id="nav">
                                        <li class="first"><a style="text-decoration: none;" href="cart?uid=${sessionScope.user.id} ">
                                                <i class="fa-solid fa-cart-shopping"></i>
                                            </a>
                                        </li>
                                        <li class="second"> 
                                            <a  style="text-decoration: none; padding: 10px 10px  ; margin: 0px" href="" >
                                                <i class="fa-solid fa-user"></i>${sessionScope.user.name}

                                            </a>
                                            <ul class="subnave">
                                                <li><a onclick="setupdate('${sessionScope.user.id}')" href="javascript:void(0);" >Information</a></li>
                                                <li><a href="login">Log Out</a></li>
                                            </ul>
                                        </li>

                                    </ul>


                                </c:if>


                                <a href="menu" class="order_online">
                                    Order Online
                                </a>
                            </div>
                        </div>
                    </nav>
                </div>
            </header>
            <!-- end header section -->
        </div>

        <!-- food section -->

        <div class="home-content container " style="height: 100vh; margin-top: 80px">
            <div style="width: 30vw" class="container mt-3">
                <c:if test="${requestScope.mess != null}">
                    <div class="alert alert-danger" role="alert">
                        ${requestScope.mess}
                    </div>
                </c:if>

                <!-- Add the form tag -->
                <form id="paymentForm" action="pay" method="POST">
                    <div class="mb-3">
                        <label class="form-label">ID Customer
                        <input name="uid" type="text" value="${requestScope.uid}" class="form-control">
                        </label>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Total price you need to pay!</label>
                        <input name="total" type="text" value="${requestScope.total}" class="form-control">
            </div>
            <div class="mb-3">
                <label class="form-label">You need to input address</label>
                <textarea class="form-control" id="id" name="address"></textarea>
            </div>
            <div class="mb-3">
                <label class="form-label">You must input time you can receive the product!</label>
                <input name="date" type="date" pattern="dd-mm-yy" class="form-control">
            </div>

            <div style="display: flex; margin: 10px">
                <button type="button" onclick="setPay('${requestScope.uid}')" data-bs-toggle="modal" data-bs-target="#exampleModal" class="btn btn-secondary">
                    <i class="fa-solid fa-check"></i>
                </button>
            </div>
        </form>
    </div>
</div>

<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="exampleModalLabel">Message</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                ...
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                <!-- Trigger form submission -->
                <button onclick="PayP()" type="button" class="btn btn-success">Pay</button>
            </div>
        </div>
    </div>
</div>

<script>
    function setPay(id) {
        document.querySelector(".modal-body").innerText = "Do you want to pay for the product with ID: " + id;
    }

    function PayP() {
        // Submit the form to the server
        document.getElementById("paymentForm").submit();
    }
</script>



        <!-- end food section -->

        <!-- footer section -->
        <!-- footer section -->


        <!--remove line on cart tab--> <!--use only on this tab-->
       

        <footer class="footer_section">
            <div class="container">
                <div class="row">
                    <div class="col-md-4 footer-col">
                        <div class="footer_contact">
                            <h4>
                                Contact Us
                            </h4>
                            <div class="contact_link_box">
                                <a href="https://www.google.com/maps/search/?api=1&query=123+Model+Car+Lane,+Hobby+City,+USA" target="_blank">
                                    <i class="fa fa-map-marker" aria-hidden="true"></i>
                                    <span>
                                        123 Model Car Lane, Hobby City, USA
                                    </span>
                                </a>
                                <a href="tel:+18005551234">
                                    <i class="fa fa-phone" aria-hidden="true"></i>
                                    <span>
                                        Call +1 (800) 555-1234
                                    </span>
                                </a>
                                <a href="mailto:support@modelcarshop.com">
                                    <i class="fa fa-envelope" aria-hidden="true"></i>
                                    <span>
                                        support@modelcarshop.com
                                    </span>
                                </a>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4 footer-col">
                        <div class="footer_detail">
                            <a href="" class="footer-logo" style="text-decoration: none;">
                                ShopCar
                            </a>
                            <p>
                                Discover a wide variety of high-quality, collectible model cars. Perfect for hobbyists and collectors alike.
                            </p>
                            <div class="footer_social">
                                <a href="">
                                    <i class="fa-brands fa-facebook-f"></i>
                                </a>
                                <a href="">
                                    <i class="fa-brands fa-twitter"></i>
                                </a>
                                <a href="">
                                    <i class="fa-brands fa-linkedin-in"></i>
                                </a>
                                <a href="">
                                    <i class="fa-brands fa-square-instagram"></i>
                                </a>
                                <a href="">
                                    <i class="fa-brands fa-pinterest"></i>
                                </a>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4 footer-col">
                        <h4>
                            Opening Hours
                        </h4>
                        <p>
                            Monday - Friday
                        </p>
                        <p>
                            9:00 AM - 6:00 PM
                        </p>
                        <p>
                            Saturday: 10:00 AM - 4:00 PM
                        </p>
                        <p>
                            Sunday: Closed
                        </p>
                    </div>
                </div>
                <div class="footer-info">
                    <p>
                        &copy; <span id="displayYear"></span> All Rights Reserved By
                        <a href="https://modelcarshop.com/">ShopCar</a>
                    </p>
                </div>
            </div>
        </footer>


        <!-- footer section -->

        <!-- jQery -->
        <script src="js/jquery-3.4.1.min.js"></script>
        <!-- popper js -->
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous">
        </script>
        <!-- bootstrap js -->
        <script src="js/bootstrap.js"></script>
        <!-- owl slider -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/owl.carousel.min.js">
        </script>
        <!-- isotope js -->
        <script src="https://unpkg.com/isotope-layout@3.0.4/dist/isotope.pkgd.min.js"></script>
        <!-- nice select -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-nice-select/1.1.0/js/jquery.nice-select.min.js"></script>
        <!-- custom js -->

        <!-- Google Map -->
        <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCh39n5U-4IoWpsVGUHWdqB6puEkhRLdmI&callback=myMap">
        </script>
        <!-- End Google Map -->
        

        <!-- Modal -->
        <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="exampleModalLabel">Messenger</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal">
                        ...
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button onclick="alterP()" type="button" class="btn btn-danger">Delete</button>
                    </div>
                </div>
            </div>
        </div>
    </body>
    
    <!-- Modal -->
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>

</html>
