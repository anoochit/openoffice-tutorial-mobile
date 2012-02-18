	<script type="text/javascript">
		$('#map').live("pageshow", function() {
		        $('#map_canvas').gmap('refresh');
		});
		$('#map').live("pagecreate", function() {
		        $('#map_canvas').gmap('addMarker', { /*id:'m_1',*/ 'position': '13.903024,100.532115', 'bounds': true } );
		});
	</script>
