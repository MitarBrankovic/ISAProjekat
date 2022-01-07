Vue.component("ReportsOfClient", {
    data: function() {
        return {
            activeUser:"",
            cottageReports:[],
            boatReports:[],
            fishingReports:[],
        }
    },
    template:`  
        <div style="margin-top: 30px;">

            <h2 style="margin-top: 1%; margin-bottom: 2%; color:#5cb85c"  class="flex title-div bigtitle">Penali na terminima vikendica</h2>

            <div class="container-fluid">
            <table class="table table-success table-striped table-sm table-bordered">
                <thead>
                <tr>
                    <td>Naziv vikendice</td>
                    <td>Datum</td>
                    <td>Ocena</td>
                    <td>Vlasnik</td>
                    <td>Cena</td>
                </tr>
                </thead>
                <tbody>
                    <tr v-for="report in cottageReports">
                    <td><a class="linkSubscription" v-on:click="showCottageInformation(report.appointment.cottage.id)">{{report.appointment.cottage.name}}</a></td>
                    <td>{{report.appointment.appointmentStart}}</td>
                    <td>{{report.appointment.cottage.rating}}</td>
                    <td>{{report.owner.name}} {{report.owner.surname}}</td>
                    <td>{{report.appointment.price}} din.</td>
                    </tr>
                </tbody>
            </table>
            </div>


            <h2 class="flex title-div bigtitle" style="margin-top: 50px; margin-bottom: 2%; color:#5cb85c">Penali na terminima brodova</h2>

            <div class="container-fluid">
            <table class="table table-success table-striped table-sm table-bordered">
                <thead>
                <tr>
                    <td>Naziv broda</td>
                    <td>Datum</td>
                    <td>Ocena</td>
                    <td>Vlasnik</td>
                    <td>Cena</td>
                </tr>
                </thead>
                <tbody>
                    <tr v-for="report in boatReports">
                    <td><a class="linkSubscription" v-on:click="showCottageInformation(report.appointment.boat.id)">{{report.appointment.boat.name}}</a></td>
                    <td>{{report.appointment.appointmentStart}}</td>
                    <td>{{report.appointment.boat.rating}}</td>
                    <td>{{report.owner.name}} {{report.owner.surname}}</td>
                    <td>{{report.appointment.price}} din.</td>
                    </tr>
                </tbody>
            </table>
            </div>

            <h2 class="flex title-div bigtitle" style="margin-top: 50px; margin-bottom: 2%; color:#5cb85c">Penali na terminima avantura</h2>

            <div class="container-fluid">
            <table class="table table-success table-striped table-sm table-bordered">
                <thead>
                <tr>
                    <td>Naziv avanture</td>
                    <td>Datum</td>
                    <td>Ocena</td>
                    <td>Vlasnik</td>
                    <td>Cena</td>
                </tr>
                </thead>
                <tbody>
                    <tr v-for="report in fishingReports">
                    <td><a class="linkSubscription" v-on:click="showCottageInformation(report.appointment.fishingAdventure.id)">{{report.appointment.fishingAdventure.name}}</a></td>
                    <td>{{report.appointment.appointmentStart}}</td>
                    <td>{{report.appointment.fishingAdventure.rating}}</td>
                    <td>{{report.owner.name}} {{report.owner.surname}}</td>
                    <td>{{report.appointment.price}} din.</td>
                    </tr>
                </tbody>
            </table>
            </div>
        </div>
    `       
        ,
    mounted(){
        this.activeUser = JSON.parse(localStorage.getItem('activeUser'))
        if(this.activeUser.role != 'client')
        this.$router.push('/')
        
        axios.all([axios.get('/reports/findAllReportsByClient/' + this.activeUser.id)])
        .then(axios.spread((...responses) => {
           this.cottageReports = responses[0].data.cottageReports
           this.boatReports = responses[0].data.boatReports
           this.fishingReports = responses[0].data.fishingReports
       }))
	},
    methods:{
        showCottageInformation(id){
			this.$router.push("/profileCottage?id=" + id)
		},
        showBoatInformation(id){
			this.$router.push("/profileBoat?id=" + id)
		},
        showAdventureInformation(id){
			this.$router.push("/selectedFishingAdventure?id=" + id)
		}

    }
});
