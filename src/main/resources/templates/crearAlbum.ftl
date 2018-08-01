<#include "layout.ftl">

<body class="bg-light">
<br>
<br>

<div class="card mx-auto"style="width:50%">
    <div class="card-header bg-dark" >
        <p class="text-white">Fotos</p></div>
    <br>
    <div class="card-body">

        <form method="post"  enctype='multipart/form-data' action="/crearAlbum">
            <div class="form-group">
            <input type="text" id="nombre" name="nombre" class="mx-auto form-control" placeholder="Nombre de album">
            </div>
            <div id="drop-zone"  class="form-group">
                <label>Agrega una foto al album</label>
                <br>
            <input type='file' id="uf" name='uf' accept=".jpg, .jpeg, .png" >
            </div>


            <div class="form-group">
                <input type="text" id="descripcion" name="descripcion" class="mx-auto form-control" placeholder="Descripcion de album" required>
            </div>

            <select class="js-example-basic form-control" name="amigos[]" multiple="multiple">
                <#list amigos as amigo>
                    <option value="${amigo.getUsername()}">${amigo.getProfile().getNombre()} ${amigo.getProfile().getApellido()}</option>
                </#list>
            </select>


    </div>

    <div class="modal-footer">

        <button type="submit" class="btn btn-info btn-xs">Publicar</button>
        </form>
    </div>
</div>

</body>


<script>
    $(document).ready(function() {

        $('.js-example-basic').select2({
            placeholder: 'Personas en este album',
            tokenSeparators: [','],
            maximumSelectionLength: 1,
            data: [],
            separator: ','
        });

    });

    $(function () {
        var dropZoneId = "drop-zone";
        var mouseOverClass = "mouse-over";

        var dropZone = $("#" + dropZoneId);
        var ooleft = dropZone.offset().left;
        var ooright = dropZone.outerWidth() + ooleft;
        var ootop = dropZone.offset().top;
        var oobottom = dropZone.outerHeight() + ootop;
        var inputFile = dropZone.find("input");
        document.getElementById(dropZoneId).addEventListener("dragover", function (e) {
            e.preventDefault();
            e.stopPropagation();
            dropZone.addClass(mouseOverClass);
            var x = e.pageX;
            var y = e.pageY;

            if (!(x < ooleft || x > ooright || y < ootop || y > oobottom)) {
                inputFile.offset({ top: y - 15, left: x - 100 });
            } else {
                inputFile.offset({ top: -400, left: -400 });
            }

        }, true);

        if (buttonId != "") {
            var clickZone = $("#" + buttonId);

            var oleft = clickZone.offset().left;
            var oright = clickZone.outerWidth() + oleft;
            var otop = clickZone.offset().top;
            var obottom = clickZone.outerHeight() + otop;

            $("#" + buttonId).mousemove(function (e) {
                var x = e.pageX;
                var y = e.pageY;
                if (!(x < oleft || x > oright || y < otop || y > obottom)) {
                    inputFile.offset({ top: y - 15, left: x - 160 });
                } else {
                    inputFile.offset({ top: -400, left: -400 });
                }
            });
        }

        document.getElementById(dropZoneId).addEventListener("drop", function (e) {
            $("#" + dropZoneId).removeClass(mouseOverClass);
        }, true);

    })



</script>


