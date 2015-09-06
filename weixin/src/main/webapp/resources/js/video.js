(function($)
{
	$.Redactor.prototype.video = function()
	{
		return {
			reUrlYoutube: /https?:\/\/(?:[0-9A-Z-]+\.)?(?:youtu\.be\/|youtube\.com\S*[^\w\-\s])([\w\-]{11})(?=[^\w\-]|$)(?![?=&+%\w.-]*(?:['"][^<>]*>|<\/a>))[?=&+%\w.-]*/ig,
			reUrlVimeo: /https?:\/\/(www\.)?vimeo.com\/(\d+)($|\/)/,
			getTemplate: function()
			{
				return String()
				+ '<section id="redactor-modal-video-insert">'
				+ '<div id="redactor-video-manager-box" style="overflow: auto; height: 300px;" ></div>'+
				+ '</section>';
			},
			init: function()
			{
				if (!this.opts.videoManagerJson) return;
				var button = this.button.addAfter('image', 'video', this.lang.get('video'));
				this.button.addCallback(button, this.video.show);
			},
			show: function()
			{
				this.modal.addTemplate('video', this.video.getTemplate());

				this.modal.load('video', this.lang.get('video'), 700);
				this.modal.createCancelButton();

				var button = this.modal.createActionButton(this.lang.get('insert'));
				button.on('click', this.video.insert);
				
				$.ajax({
					  dataType: "json",
					  cache: false,
					  url: this.opts.videoManagerJson,
					  success: $.proxy(function(data)
						{
							$.each(data, $.proxy(function(key, val)
							{
								// title
								var thumbtitle = '';
								if (typeof val.title !== 'undefined') thumbtitle = val.title;

								var img = $('<img src="' + val.thumb + '" rel="' + val.image + '" title="' + thumbtitle + '" style="width: 100px; height: 75px; cursor: pointer;" />');
								$('#redactor-video-manager-box').append(img);
								$(img).click($.proxy(this.video.insert, this));

							}, this));


						}, this)
					});
				
				
				this.selection.save();
				this.modal.show();

				$('#redactor-insert-video-area').focus();

			},
			insert: function(e)
			{
				// this.video.insert('<img style="width:100%" src="' + $(e.target).attr('rel') + '" alt="' + $(e.target).attr('title') + '">');
				this.insert.html('<video src="'+$(e.target).attr('rel')+'"></video>');
			}

		};
	};
})(jQuery);