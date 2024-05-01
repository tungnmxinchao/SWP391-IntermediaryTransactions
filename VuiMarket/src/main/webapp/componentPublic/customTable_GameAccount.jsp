<%-- 
    Document   : customTable_GameAccount
    Created on : Jan 27, 2024, 7:39:38 PM
    Author     : TNO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- Custom datatable -->
<style>
    table {
        border: 1px solid black;
        width: 80%;
        margin-left: auto;
        margin-right: auto;

    }

    th, td {
        border: 1px solid black;
        padding: 8px;
        text-align: left;
    }

    table2 {
        border: 1px solid black;
        width: 70%;
        margin-left: auto;
        margin-right: auto;

    }



    .price_search{
        display: flex;
    }
    .form-select {
        padding: 8px;
        margin: 10px 0;
        font-size: 16px;
        border: 1px solid #ccc;
        border-radius: 4px;
        box-sizing: border-box;
        width: 200px; /* Điều chỉnh độ rộng theo nhu cầu */
    }

    /* Tùy chỉnh khi hover */
    .form-select:hover {
        border-color: #333;
    }

    /* Tùy chỉnh khi được chọn */
    .form-select:focus {
        outline: none;
        border-color: #0066cc;
        box-shadow: 0 0 5px rgba(0, 102, 204, 0.7);
    }

    /* Tùy chỉnh màu nền của tùy chọn khi được chọn */
    .form-select option:checked {
        background-color: #0066cc;
        color: #fff;
    }

    .tag_select{
        padding-left:  150px;
    }

    .form-select{
        margin-right: 50px;
    }
    

</style>