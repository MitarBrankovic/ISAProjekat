Vue.component("HistoryReservations", {
    data: function() {
        return {
            activeUser:"",
            cottagesButton:true,
            boatsButton:false,
            adventuresButton:false,
            cottages:[],
            boats:[],
            adventures:[]
        }
    },
    template:`  
        <div style="margin-top: 30px;">
            <div class="nav nav-tabs centerIt">
                <button  class="button-tab nav-item" type="button" v-on:click="cottagesFun()">Cottages</button>
                <button  class="button-tab nav-item" type="button" v-on:click="boatsFun()">Boats</button>
                <button  class="button-tab nav-item" type="button" v-on:click="adventuresFun()">Adventures</button>
            </div>
            <div v-if="cottagesButton">
                <h2 class="flex title-div bigtitle">Istorija rezervacija vikendica</h2>
                <SearchCottAppointments style="margin-top: 30px;" id="search" @clicked="searchCottAppointments"></SearchCottAppointments>

                <div class="container-fluid">
                <table class="table">
                    <thead>
                    <tr>
                        <td>Naziv vikendice</td>
                        <td>Datum i vreme pocetka rezervacije</td>
                        <td>Trajanje</td>
                        <td>Maksimalan broj osoba</td>
                        <td>Cena</td>
                        <td>Oceni</td>
                    </tr>
                    </thead>
                    <tbody>
                        <tr v-for="appointment in cottages">
                        <td><a class="linkAppointment" v-on:click="showCottageInformation(appointment.cottage.id)">{{appointment.cottage.name}}</a></td>
                        <td>{{appointment.appointmentStart}}</td>
                        <td>{{appointment.duration}} h</td>
                        <td>{{appointment.maxAmountOfPeople}}</td>
                        <td>{{appointment.price}} din.</td>
                        </tr>
                    </tbody>
                </table>
                </div>
            </div>

            <div v-else-if="boatsButton">
                <h2 class="flex title-div bigtitle">Istorija rezervacija brodova</h2>
                <SearchBoatAppointments style="margin-top: 30px;" id="search" @clicked="searchBoatAppointments"></SearchBoatAppointments>

                <div class="container-fluid">
                <table class="table">
                    <thead>
                    <tr>
                        <td>Naziv broda</td>
                        <td>Datum i vreme pocetka rezervacije</td>
                        <td>Trajanje</td>
                        <td>Maksimalan broj osoba</td>
                        <td>Cena</td>
                        <td>Oceni</td>
                    </tr>
                    </thead>
                    <tbody>
                        <tr v-for="appointment in boats">
                        <td><a class="linkAppointment" v-on:click="showBoatInformation(appointment.boat.id)">{{appointment.boat.name}}</a></td>
                        <td>{{appointment.appointmentStart}}</td>
                        <td>{{appointment.duration}} h</td>
                        <td>{{appointment.maxAmountOfPeople}}</td>
                        <td>{{appointment.price}} din.</td>
                        </tr>
                    </tbody>
                </table>
                </div>
            </div>

            <div v-else-if="adventuresButton">
                <h2 class="flex title-div bigtitle">Istorija rezervacija avantura</h2>
                <SearchAdvAppointments style="margin-top: 30px;" id="search" @clicked="searchAdvAppointments"></SearchAdvAppointments>

                <div class="container-fluid">
                <table class="table">
                    <thead>
                    <tr>
                        <td>Naziv avanture</td>
                        <td>Datum i vreme pocetka rezervacije</td>
                        <td>Trajanje</td>
                        <td>Maksimalan broj osoba</td>
                        <td>Cena</td>
                        <td>Oceni</td>
                    </tr>
                    </thead>
                    <tbody>
                        <tr v-for="appointment in adventures">
                        <td><a class="linkAppointment" v-on:click="showAdventureInformation(appointment.fishingAdventure.id)">{{appointment.fishingAdventure.name}}</a></td>
                        <td>{{appointment.appointmentStart}}</td>
                        <td>{{appointment.duration}} h</td>
                        <td>{{appointment.maxAmountOfPeople}}</td>
                        <td>{{appointment.price}} din.</td>
                        </tr>
                    </tbody>
                </table>
                </div>
            </div>
        </div>
    `       
        ,
    mounted(){
        this.activeUser = JSON.parse(localStorage.getItem('activeUser'))
        if(this.activeUser.role != 'client')
        this.$router.push('/')

        axios.all([axios.get('/cottageAppointments/getAllCottAppointmentsByClient/' + this.activeUser.id),
        axios.get('/boatAppointments/getAllBoatAppointmentsByClient/' + this.activeUser.id),
        axios.get('/fishingAppointments/getAllAdvAppointmentsByClient/' + this.activeUser.id)])
        .then(axios.spread((...responses) => {
           this.cottages = responses[0].data
           this.boats = responses[1].data
           this.adventures = responses[2].data
       }))
	},
    methods:{
        cottagesFun: function(){
            this.cottagesButton = true
            this.boatsButton = false
            this.adventuresButton = false
        },
        boatsFun: function(){
            this.cottagesButton = false
            this.boatsButton = true
            this.adventuresButton = false
        },
        adventuresFun: function(){
            this.cottagesButton = false
            this.boatsButton = false
            this.adventuresButton = true
        },
        showCottageInformation(id){
			this.$router.push("/profileCottage?id=" + id)
		},
        showBoatInformation(id){
			this.$router.push("/profileBoat?id=" + id)
		},
        showAdventureInformation(id){
			this.$router.push("/selectedFishingAdventure?id=" + id)
		},
        searchCottAppointments:function(search){
            console.log(search)
            axios
            .post('/cottageAppointments/searchCottAppointments',search)
            .then(response=>{
                this.cottages = response.data
            })
        },
        searchBoatAppointments:function(search){
            console.log(search)
            axios
            .post('/boatAppointments/searchBoatAppointments',search)
            .then(response=>{
                this.boats = response.data
            })
        },
        searchAdvAppointments:function(search){
            console.log(search)
            axios
            .post('/fishingAppointments/searchAdvAppointments',search)
            .then(response=>{
                this.adventures = response.data
            })
        }
    }
});
