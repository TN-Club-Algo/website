<#macro header>
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Je suis un titre</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma@0.9.4/css/bulma.min.css">
        <script src="https://kit.fontawesome.com/701effbac8.js" crossorigin="anonymous"></script>
        <#--<link rel="icon" type="image/png" href=""/>-->
    </head>
    <body>
    <div class="container">
        <#nested>
    </div>
    </body>
    </html>
</#macro>