package com.company.component.smpt;

public interface HtmlContent {
    String htmlContent = """
            
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Email Notification</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }
        .container {
            max-width: 600px;
            margin: 20px auto;
            background: #ffffff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        .header {
            background: #007bff;
            color: #ffffff;
            text-align: center;
            padding: 10px;
            font-size: 24px;
            border-radius: 10px 10px 0 0;
        }
        .content {
            padding: 20px;
            text-align: left;
            font-size: 16px;
            color: #333;
        }
        .button {
            display: block;
            width: 200px;
            text-align: center;
            background: #007bff;
            color: #ffffff;
            padding: 10px;
            margin: 20px auto;
            text-decoration: none;
            border-radius: 5px;
        }
        .footer {
            text-align: center;
            padding: 10px;
            font-size: 14px;
            color: #777;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">🔔 Important Notification</div>
        <div class="content">
            <p>Dear User,</p>
            <p>We are pleased to inform you about an important update regarding your account.</p>
            <p>Please click the button below for more details:</p>
            <a href="https://youtu.be/xvFZjo5PgG0?si=WNwO1AtBUsmt0cVc" class="button">View Details</a>
            <p>Thank you for choosing our service!</p>
        </div>
        <div class="footer">
            &copy; 2025 Your Company. All rights reserved.
        </div>
    </div>
</body>
</html>
""";

}
