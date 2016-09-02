<%--
  Created by IntelliJ IDEA.
  User: vita
  Date: 9/1/16
  Time: 3:06 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>

<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="description" content="DB Grad web app">
    <meta name="author" content="SitePoint">
    <!-- Bootstap 3 CSS -->
    <link rel="stylesheet" href="https://bootswatch.com/yeti/bootstrap.min.css">
    <!--&lt;!&ndash; Bootstrap &ndash;&gt;-->
    <!--<link rel="stylesheet" href="bootstrap.min.css.css">-->

    <title>Escapegen</title>
</head>
<body>
<div id="dataPage">
    <!-- Navbar -->
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#">Escapegen</a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                    <li class="active"><a href="#">Play<span class="sr-only">(current)</span></a></li>
                    <li><a href="#">Last saved games<span class="sr-only">(current)</span></a></li>
                </ul>
            </div><!-- /.navbar-collapse -->
        </div><!-- /.container-fluid -->
    </nav>

    <div class="container">
        <div class="page-header" id="banner">
            <div class="row">
                <a href="#" class="btn btn-default">Generate a new game</a>
                <a href="#" class="btn btn-default">Save this game</a>
            </div>
        </div>
        <div id="gameContent" class="panel panel-primary">
            <div class="panel-heading">
                <h3 class="panel-title">Try to get out of the room</h3>
            </div>
            <div class="panel-body" style="max-height: 250pt; min-height: 250pt; overflow-y: scroll;">
            </div>
        </div>

        <div id="input" class="form-group">
            <input type="text" class="form-control" id="inputDefault" placeholder="Type command">
        </div>

</div>

<!-- jQuery JS -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
<!--<script src="script.js"></script>-->
<script>
    var commandNames = null;
    var commands = null;
    $(document).ready(function() {

        //loadActions();


        function loadActions() {
            $.post("${home}game/ping").done(function(data) {
                console.log(data);
                updateGame(JSON.parse(data));
            }).fail(function(data) {
                console.log(data);
            });
        }

        function updateGame(data) {
            var gameContentBody = $('#gameContent').find('.panel-body');
            gameContentBody.append(data.response);
            gameContentBody.scrollTop(gameContentBody[0].scrollHeight);
            $('input:text').val("");
            $('#gameContent').find('.panel-title').html(data.currentLocation);

//            if (data.inputNeeded) {
//                $('#input').show();
//                $('#command').hide();
//            } else {
//                console.log(data.inputNeeded);
//                $('#input').hide();
//                $('#command').show();
//            }

            commands = data.commands;
//            for(var i = 0; i < options.length; i++) {
//                var opt = options[i];
//                var el = document.createElement("li");
//                el.textContent = opt;
//                el.value = opt;
//                select.appendChild(el);
//            }​

        }

        $('#input').keypress(function (e) {
            if (e.which == 13) {
                console.log("keypress");
                executeCommand();
                return false;    //<---- Add this line
            }
        });
//
//        $('#input').click(executeCommand);

        function executeCommand() {
            console.log("${home}game/execute-command/" + $('input:text').val());
            $.post("${home}game/execute-command/" + $('input:text').val()).done(function(data) {
                console.log(data);
                updateGame(JSON.parse(data));
            }).fail(function(data) {
                console.log(data);
            });
        }
    });
</script>

<!--<script src="script.js"></script>-->
</body>
</html>