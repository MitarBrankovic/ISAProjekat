Vue.component("AdminsGraph", {
    data: function() {
        return {
            activeUser:"",
			chartInfo:[],
			profit: 0,
        }
	
    },
    template :`
    <div>
	<div class="row my-row  justify-content-around">
            <div class="col-sm-8 my-col">
                <h2 style="margin-top: 1%; margin-bottom: 2%">Graf prihoda - ukupan profit sistema za izabrani period: {{this.profit}} RSD</h2>
              </div>
			<div class="col-sm-1">
                <button style="margin-top: 2%" type="button" v-on:click="weekFormat()" class="btn btn-success btn-md">Nedeljni</button>
              </div>
			<div class="col-sm-1">
                <button style="margin-top: 2%" type="button" v-on:click="monthFormat()" class="btn btn-success btn-md">Mesečni</button>
              </div>
			<div class="col-sm-1">
                <button style="margin-top: 2%" type="button" v-on:click="yearFormat()" class="btn btn-success btn-md">Godišnji</button>
              </div>
			<div class="col-sm-1">
                <button style="margin-top: 2%" type="button" v-on:click="allFormat()" class="btn btn-success btn-md">Svi prihodi</button>
              </div>
    <div class="container-fluid" id="chartDiv" style="width: 100%; height: 1000;"></div>
	</div>	  
    	`
    	,
    methods: {
		formatDate(date) {
			let formattedDate = date.substring(8,10) + "." + date.substring(5,7) + "." + date.substring(0,4)
			return formattedDate;
		},

		
		renderChart(series) { 
	  return JSC.chart('chartDiv', { 
	    debug: true, 
	    type: 'column solid', 
	    legend_visible: false, 
	    palette: ['lightgreen'], 
		height: 700,
	    defaultPoint: { 
	      tooltip: 
	        'Datum: <b>%xValue</b> <br>Prihod (RSD): <b>%yValue</b><br><b>', 
	      complete: { 
	        fill: 'rgba(255,255,255,.3)', 
	        hatch_style: 'none'
	      }, 
	      label: { 
	        align: 'center', 
	        verticalAlign: 'bottom', 
	        autoHide: false, 
	        style_fontSize: '15px'
	      } 
	    }, 
	    series: series 
	  }); 
	} ,

	makeSeries(data) { 
		  return [ 
		    { 
		      points: data.map(function(item) { 
		        return { 
		          x: item.dateAndTime.substring(8,10) + "." + item.dateAndTime.substring(5,7) + "." + item.dateAndTime.substring(0,4), 
		          y: item.price, 
		        }; 
		      }) 
		    } 
		  ]; 
		} ,
		
	weekFormat() {
		instructorsId = this.activeUser.id 
	    axios
        	.get('/admin/getProfitWeekCharts')
	        .then(response => {
	        this.chartInfo = response.data
	        console.log(this.chartInfo)
			this.renderChart(this.makeSeries(this.chartInfo));
			this.renderProfit()
	        });
	},
	
	monthFormat() {
		instructorsId = this.activeUser.id 
        axios
        	.get('/admin/getProfitMonthCharts')
	        .then(response => {
	        this.chartInfo = response.data
	        console.log(this.chartInfo)
			this.renderChart(this.makeSeries(this.chartInfo));
			this.renderProfit()
	        });
	},
	
	yearFormat() {
		instructorsId = this.activeUser.id 
        axios
        	.get('/admin/getProfitYearCharts')
	        .then(response => {
	        this.chartInfo = response.data
	        console.log(this.chartInfo)
			this.renderChart(this.makeSeries(this.chartInfo));
			this.renderProfit()
	        });
	},
	allFormat() {
		instructorsId = this.activeUser.id 
        axios
        	.get('/admin/getProfit')
	        .then(response => {
	        this.chartInfo = response.data
	        console.log(this.chartInfo)
			this.renderChart(this.makeSeries(this.chartInfo));
			this.renderProfit()
	        });
	},
	renderProfit() {
		this.profit = 0;
		for (var i = 0; i < this.chartInfo.length; i ++ ){
			this.profit += this.chartInfo[i].price
		}
	},
    },

    mounted () {
		this.activeUser = JSON.parse(localStorage.getItem('activeUser'))
        if(this.activeUser.role != 'admin')
            this.$router.push('/')

        instructorsId = this.activeUser.id 
        axios
        	.get('/admin/getProfit')
	        .then(response => {
	        this.chartInfo = response.data
	        console.log(this.chartInfo)
			this.renderChart(this.makeSeries(this.chartInfo));
			this.renderProfit()
	        });
},


});



