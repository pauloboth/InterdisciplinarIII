if (typeof($.fn.dataTable) !== 'undefined') {
    $.extend($.fn.dataTable.defaults, {
        "language": {
            "emptyTable": "Nenhum registro encontrado",
            "info": "Mostrando _START_ - _END_ de _TOTAL_ registros",
            "infoEmpty": "Mostrando 0 - 0 de 0 registros",
            "infoFiltered": "(Filtrados de _MAX_ registros)",
            "infoPostFix": "",
            "infoThousands": ".",
            "lengthMenu": "_MENU_ registros por página",
            "loadingRecords": "Carregando...",
            "processing": "Processando...",
            "zeroRecords": "Nenhum registro encontrado",
            "search": "<i class='fa fa-search'></i>",
            "paginate": {
                "next": "Próxima",
                "previous": "Anterior",
                "first": "",
                "last": ""
            },
            "aria": {
                "sortAscending": ": Ordenar colunas de forma ascendente",
                "sortDescending": ": Ordenar colunas de forma descendente"
            }
        },
        "pageLength": 50,
        "lengthMenu": [ 5, 10, 50, 100 ],
        "renderer": "bootstrap",
        "pagingType": "full_numbers",
        "columnDefs": [ {
            "targets": [-1, -2],
            "orderable": false,
            "searchable": false
        } ],
        "dom": '<"dataTables_wrapper form-inline dt-bootstrap no-footer"<"row"<"col-sm-6"i><"col-sm-6"f>><"row"<"col-sm-12"rt>><"row"<"col-sm-5"l><"col-sm-7"p>>>'
    });
}

function toggleDropdown($a) {
    var $parent = $a.parent();
    if ($parent.hasClass('open')) {
//        $a.children('.dropdown-chevron')
//            .addClass('fa-chevron-left')
//            .removeClass('fa-chevron-down')
//                ;
        $parent.children('.submenu')
            .slideUp(400, function() {
                $parent.removeClass('open');
            });
    } else {
//        $a.children('.dropdown-chevron')
//            .removeClass('fa-chevron-left')
//            .addClass('fa-chevron-down')
//                ;
        $parent.children('.submenu')
            .slideDown(400, function() {
                $parent.addClass('open');
            });
    }
}
$(document).ready(function(){
    
    $('.alert-dismissable .close').click(function() {
        $(this).parent().fadeOut(200);
        return false;
    });
    
    $('.ui-treenode-label').click(function(){
        if ($(this).parent().children('.ui-tree-toggler').length) {
            $(this).parent().children('.ui-tree-toggler').click();
        }
    });
    
    //Login
    $('.form-login input')
        .focus(function() {
            $(this).parent().addClass('focused');
        })
        .blur(function() {
            $(this).parent().removeClass('focused');
        })
    ;
    
    $('.menu-dropdown > a').click(function(e) {
        e.preventDefault();
        $a = $(this);
        $menuDropdown = $a.parent();
        $('.menu-dropdown.open > a', $menuDropdown.parent()).each(function() { //fecha os menus ativos
            toggleDropdown($(this));
            if ($(this).is($a)) {
                $a = null;
            }
        });
        if ($a)
            toggleDropdown($a);
    });
    
    if (typeof($.fn.dataTable) !== 'undefined') {
        // Fuck you Java
        // http://stackoverflow.com/questions/20549752/empty-hdatatable-in-jsf-renders-empty-row-with-invalid-number-of-columns
        $('.dataTable tbody').each(function(idx, item) {
            if ($(this).children('tr:first').children().length === 1) { //Quer dizer que o Java fez cagada no HTML (<tbody><tr><td></td></tr></tbody>)
                $(this).html('');
            }
        });
        
        $('.dataTable').DataTable();
    }
    
    
    /*
    // Ripple effect
    var parent, ink, d, x, y;
    $(".ripple a").click(function (e) {
        parent = $(this).parent();
        
        //create .ink element if it doesn't exist
        if (parent.find(".ink").length == 0)
            parent.prepend("<span class='ink'></span>");

        ink = parent.find(".ink");
        //incase of quick double clicks stop the previous animation
        ink.removeClass("animate");

        //set size of .ink
        if (!ink.height() && !ink.width())
        {
            //use parent's width or height whichever is larger for the diameter to make a circle which can cover the entire element.
            d = Math.max(parent.outerWidth(), parent.outerHeight());
            ink.css({height: d, width: d});
        }

        //get click coordinates
        //logic = click coordinates relative to page - parent's position relative to page - half of self height/width to make it controllable from the center;
        x = e.pageX - parent.offset().left - ink.width() / 2;
        y = e.pageY - parent.offset().top - ink.height() / 2;

        //set the position and add class .animate
        ink.css({top: y + 'px', left: x + 'px'}).addClass("animate");
    });*/
});