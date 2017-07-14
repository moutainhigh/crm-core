$(document).ready(function () {

    (function ($) {

        $('#filter').keyup(function () {

            var rex = new RegExp($(this).val(), 'i');
            $('.searchableWorker tr').hide();
            $('.searchableWorker tr').filter(function () {
                return rex.test($(this).text());
            }).show();

        })

    }(jQuery));

});

$(document).ready(function () {

    (function ($) {

        $('#filter2').keyup(function () {

            var rex = new RegExp($(this).val(), 'i');
            $('.searchableManager tr').hide();
            $('.searchableManager tr').filter(function () {
                return rex.test($(this).text());
            }).show();

        })

    }(jQuery));

});

$(document).ready(function () {

    (function ($) {

        $('#filter3').keyup(function () {

            var rex = new RegExp($(this).val(), 'i');
            $('.searchableBoss tr').hide();
            $('.searchableBoss tr').filter(function () {
                return rex.test($(this).text());
            }).show();

        })

    }(jQuery));

});

$(document).ready(function () {

    (function ($) {

        $('#filter4').keyup(function () {

            var rex = new RegExp($(this).val(), 'i');
            $('.searchablePosition tr').hide();
            $('.searchablePosition tr').filter(function () {
                return rex.test($(this).text());
            }).show();

        })

    }(jQuery));

});
$(document).ready(function () {

	(function ($) {

		$('#filter5').keyup(function () {

			var rex = new RegExp($(this).val(), 'i');
			$('.searchableIng tr').hide();
			$('.searchableIng tr').filter(function () {
				return rex.test($(this).text());
			}).show();

		})

	}(jQuery));

});

