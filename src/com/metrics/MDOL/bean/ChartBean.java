package com.metrics.MDOL.bean;

public class ChartBean {
	private String chartJs;
	
	public String theme = "<script type=\"text/javascript\">Highcharts.theme = {" +
			"colors: ['#058DC7', '#50B432', '#ED561B', '#DDDF00', '#24CBE5', '#64E572', '#FF9655', '#FFF263', '#6AF9C4']," +
			"chart: {" +
			"backgroundColor: {" +
			"linearGradient: { x1: 0, y1: 0, x2: 1, y2: 1 }," +
			"stops: [" +
            "[0, 'rgb(255, 255, 255)']," +
            "[1, 'rgb(240, 240, 255)']" +
         "]" +
      "}," +
      "borderWidth: 2," +
      "plotBackgroundColor: 'rgba(255, 255, 255, .9)'," +
      "plotShadow: true," +
      "plotBorderWidth: 1" +
   "}," +
   "title: {" +
      "style: {" +
         "color: '#000'," +
         "font: 'bold 16px \"Trebuchet MS\", Verdana, sans-serif'" +
      "}" +
   "}," +
   "subtitle: {" +
      "style: {" +
         "color: '#666666'," +
         "font: 'bold 12px \"Trebuchet MS\", Verdana, sans-serif'" +
      "}" +
   "}," +
   "xAxis: {" +
      "gridLineWidth: 1," +
      "lineColor: '#000'," +
      "tickColor: '#000'," +
      "labels: {" +
         "style: {" +
            "color: '#000'," +
            "font: '11px Trebuchet MS, Verdana, sans-serif'" +
         "}" +
      "}," +
      "title: {" +
         "style: {" +
            "color: '#333'," +
            "fontWeight: 'bold'," +
            "fontSize: '12px'," +
            "fontFamily: 'Trebuchet MS, Verdana, sans-serif'" +
         "}" +
      "}" +
   "}," +
   "yAxis: {" +
      "minorTickInterval: 'auto'," +
      "lineColor: '#000'," +
      "lineWidth: 1," +
      "tickWidth: 1," +
      "tickColor: '#000'," +
      "labels: {" +
         "style: {" +
            "color: '#000'," +
            "font: '11px Trebuchet MS, Verdana, sans-serif'" +
         "}" +
      "}," +
      "title: {" +
         "style: {" +
            "color: '#333'," +
            "fontWeight: 'bold'," +
            "fontSize: '12px'," +
            "fontFamily: 'Trebuchet MS, Verdana, sans-serif'" +
         "}" +
      "}" +
   "}," +
   "legend: {" +
      "itemStyle: {" +
         "font: '9pt Trebuchet MS, Verdana, sans-serif'," +
         "color: 'black'" +

      "}," +
      "itemHoverStyle: {" +
         "color: '#039'" +
      "}," +
      "itemHiddenStyle: {" +
         "color: 'gray'" +
      "}" +
   "}," +
   "labels: {" +
      "style: {" +
         "color: '#99b'" +
      "}" +
   "}," +

   "navigation: {" +
      "buttonOptions: {" +
         "theme: {" +
            "stroke: '#CCCCCC'" +
         "}" +
      "}" +
   "}" +
"};" +

// Apply the theme
"var highchartsOptions = Highcharts.setOptions(Highcharts.theme);</script>";
	
	public String getCandlestick(String host, String instrument, String freq, String unit, String dateStart, String dateEnd) {
		String js = "<script type=\"text/javascript\" src=\"http://code.jquery.com/jquery-1.9.1.min.js\"></script>\n" +
					"<script src=\"http://code.highcharts.com/stock/modules/exporting.js\"></script>\n" +
					"<script src=\"http://code.highcharts.com/stock/highstock.js\"></script>\n" +
					"<script type=\"text/javascript\">\n" +
					"$(document).ready(function(){\n" + 
					"$.getJSON('http://"+ host +"/chart?instrument="+ instrument +"&dateStart="+ dateStart + "&dateEnd=" + dateEnd + "', function(data) {\n" + 
					"$('#container').highcharts('StockChart', {\n" +
					"rangeSelector : {\n" +
					"selected : 1\n" +
					"},\n" +
					"title : {\n" +
					"text : '" + instrument + " Stock Price'\n" +
					"},\n" + 
					"series : [{\n" +
					"type : 'candlestick',\n" +
					"name : '" + instrument + " Stock Price',\n" +
					"data : data.jsonResult.ts,\n" +
					"dataGrouping : {\n" +
					"units : [\n" +
						"['week',\n" +
						"[1]\n" +
					"], [\n" +
						"'month',\n" + 
						"[1, 2, 3, 4, 6]]\n" + 
					"]\n" +
					"}\n" +
					"}]\n" +
					"});\n" +
					"});\n" +
					"});\n" +
					"</script>";
		return js;
	}
	
	public String getTowPanesCandlestickAndVolume(String jsonurl, String instrument, String freq, String unit, String dateStart, String dateEnd) {
		String js = "$.getJSON('" + jsonurl + "?instrument=" + instrument + "&freq=" + freq + "&unit=" + unit + "&dateStart=" + dateStart + "&dateEnd=" + dateEnd + "', function(data) {" +
				
			"var ohlc = [],	volume = [], dataLength = data.length;" +
				
			"for (i = 0; i < dataLength; i++) {"+
				"ohlc.push([" +
					"data[i][0]," + // the date
					"data[i][1]," + // open
					"data[i][2]," + // high
					"data[i][3]," + // low
					"data[i][4]" + // close
				"]);" +
				
				"volume.push([" +
					"data[i][0]," + // the date
					"data[i][5]" + // the volume
				"])" +
			"}" +

			// set the allowed units for data grouping
			"var groupingUnits = [[" +
				"'week'," +                         // unit name
				"[1]" +                             // allowed multiples
			"], [" +
				"'month'," +
				"[1, 2, 3, 4, 6]" +
			"]];" +

			// create the chart
			"$('#container').highcharts('StockChart', {" + 
			    "rangeSelector: {" +
			        "selected: 1" +
			    "}," +
			    "title: {" +
			        "text: 'AAPL Historical'" +
			    "}," +
			    "yAxis: [{" +
			        "title: {" +
			            "text: 'OHLC'" +
			        "}," +
			        "height: 200," +
			        "lineWidth: 2" +
			    "}, {" +
			        "title: {" +
			            "text: 'Volume'" +
			        "}," +
			        "top: 300," +
			        "height: 100," +
			        "offset: 0," +
			        "lineWidth: 2" +
			    "}]," +
			    "series: [{" +
			        "type: 'candlestick'," +
			        "name: 'AAPL'," +
			        "data: ohlc," +
			        "dataGrouping: {" +
						"units: groupingUnits" +
			        "}" +
			    "}, {" +
			        "type: 'column'," +
			        "name: 'Volume'," +
			        "data: volume," +
			        "yAxis: 1," +
			        "dataGrouping: {" +
						"units: groupingUnits" +
			        "}" +
			    "}]" +
			"});" +
		"});";
		return js;
	}
	
	public String getChartJs() {
		return chartJs;
	}

	public void setChartJs(String chartJs) {
		this.chartJs = chartJs;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}
	
	
}
