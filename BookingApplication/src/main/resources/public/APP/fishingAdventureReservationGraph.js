Vue.component("FishingAdventureReservationGraph", {
    data: function() {
        return {
            activeUser:"",
			chartInfo:[],
        }
	
    },
    template :`
    <div>
	<div class="row my-row  justify-content-around">
            <div class="col-sm-8 my-col">
                <h2 style="margin-top: 1%; margin-bottom: 2%">Graf posecenosti vikendica</h2>
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
                <button style="margin-top: 2%" type="button" v-on:click="allFormat()" class="btn btn-success btn-md">Sve posecenosti</button>
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
	    palette: ['lightgreen','red'], 
		height: 700,
	    defaultPoint: { 
	      tooltip: 
	        'Ime: <b>%xValue</b> <br>Broj posecenosti : <b>%yValue</b><br><b>', 
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
		          x: item.name, 
		          y: item.amount, 
		        }; 
		      }) 
		    } 
		  ]; 
		} ,
		
	weekFormat() {
		instructorsId = this.activeUser.id 
	    axios
        	.get('/fishingAppointments/getFishingAdventureReservationChartsWeek/' + instructorsId)
	        .then(response => {
	        this.chartInfo = response.data
	        console.log(this.chartInfo)
			this.renderChart(this.makeSeries(this.chartInfo));
	        });
	},
	
	monthFormat() {
		instructorsId = this.activeUser.id 
        axios
        	.get('/fishingAppointments/getFishingAdventureReservationChartsMonth/' + instructorsId)
	        .then(response => {
	        this.chartInfo = response.data
	        console.log(this.chartInfo)
			this.renderChart(this.makeSeries(this.chartInfo));
	        });
	},
	
	yearFormat() {
		instructorsId = this.activeUser.id 
        axios
        	.get('/fishingAppointments/getFishingAdventureReservationChartsYear/' + instructorsId)
	        .then(response => {
	        this.chartInfo = response.data
	        console.log(this.chartInfo)
			this.renderChart(this.makeSeries(this.chartInfo));
	        });
	},
	allFormat() {
		instructorsId = this.activeUser.id 
        axios
        	.get('/fishingAppointments/getFishingAdventureReservationCharts/' + instructorsId)
	        .then(response => {
	        this.chartInfo = response.data
	        console.log(this.chartInfo)
			this.renderChart(this.makeSeries(this.chartInfo));
	        });
	},
    },

    mounted () {
		this.activeUser = JSON.parse(localStorage.getItem('activeUser'))
        if(this.activeUser.role != 'fishing_instructor')
            this.$router.push('/')

        instructorsId = this.activeUser.id 
        axios
        	.get('/fishingAppointments/getFishingAdventureReservationCharts/' + instructorsId)
	        .then(response => {
	        this.chartInfo = response.data
	        console.log(this.chartInfo)
			this.renderChart(this.makeSeries(this.chartInfo));
	        });
},


});



