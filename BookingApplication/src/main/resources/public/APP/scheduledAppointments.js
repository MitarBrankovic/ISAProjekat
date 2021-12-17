Vue.component("ScheduledAppointments", {
    data: function() {
        return {
            activeUser:"",

            cottagesButton:true,
            boatsButton:false,
            adventuresButton:false,
            cottageDtos:[],
            boatDtos:[],
            adventuresDtos:[]
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
                <h2 class="flex title-div bigtitle">Rezervisani termini - vikendice</h2>
                <div class="container-fluid">
                <table class="table">
                    <thead>
                    <tr>
                        <td>Naziv vikendice</td>
                        <td>Datum i vreme pocetka rezervacije</td>
                        <td>Trajanje</td>
                        <td>Maksimalan broj osoba</td>
                        <td>Cena</td>
                        <td>Otkazi</td>
                    </tr>
                    </thead>
                    <tbody>
                        <tr v-for="dto in cottageDtos">
                        <td><a class="linkAppointment" v-on:click="showCottageInformation(dto.appointment.cottage.id)">{{dto.appointment.cottage.name}}</a></td>
                        <td>{{dto.appointment.appointmentStart}}</td>
                        <td>{{dto.appointment.duration}} h</td>
                        <td>{{dto.appointment.maxAmountOfPeople}}</td>
                        <td>{{dto.appointment.price}} din.</td>
                        <td v-if="dto.dateIsCorrect"><button type="button" class="btn btn-success" v-on:click="cancelCottAppointment(dto)">Otkazi</button> </td>
                        <td v-else><p style="color:red;">Nije moguce otkazati 3 dana pre termina</p> </td>
                        </tr>
                    </tbody>
                </table>
                </div>
            </div>

            <div v-else-if="boatsButton">
                <h2 class="flex title-div bigtitle">Rezervisani termini - brodovi</h2>
                <div class="container-fluid">
                <table class="table">
                    <thead>
                    <tr>
                        <td>Naziv broda</td>
                        <td>Datum i vreme pocetka rezervacije</td>
                        <td>Trajanje</td>
                        <td>Maksimalan broj osoba</td>
                        <td>Cena</td>
                        <td>Otkazi</td>
                    </tr>
                    </thead>
                    <tbody>
                        <tr v-for="dto in boatDtos">
                        <td><a class="linkAppointment" v-on:click="showBoatInformation(dto.appointment.boat.id)">{{dto.appointment.boat.name}}</a></td>
                        <td>{{dto.appointment.appointmentStart}}</td>
                        <td>{{dto.appointment.duration}} h</td>
                        <td>{{dto.appointment.maxAmountOfPeople}}</td>
                        <td>{{dto.appointment.price}} din.</td>
                        <td v-if="dto.dateIsCorrect"><button type="button" class="btn btn-success" v-on:click="cancelBoatAppointment(dto)">Otkazi</button> </td>
                        <td v-else><p style="color:red;">Nije moguce otkazati 3 dana pre termina</p> </td>
                        </tr>
                    </tbody>
                </table>
                </div>
            </div>

            <div v-else-if="adventuresButton">
                <h2 class="flex title-div bigtitle">Rezervisani termini - avanture</h2>
                <div class="container-fluid">
                <table class="table">
                    <thead>
                    <tr>
                        <td>Naziv avanture</td>
                        <td>Datum i vreme pocetka rezervacije</td>
                        <td>Trajanje</td>
                        <td>Maksimalan broj osoba</td>
                        <td>Cena</td>
                        <td>Otkazi</td>
                    </tr>
                    </thead>
                    <tbody>
                        <tr v-for="dto in adventuresDtos">
                        <td><a class="linkAppointment" v-on:click="showAdventureInformation(dto.appointment.fishingAdventure.id)">{{dto.appointment.fishingAdventure.name}}</a></td>
                        <td>{{dto.appointment.appointmentStart}}</td>
                        <td>{{dto.appointment.duration}} h</td>
                        <td>{{dto.appointment.maxAmountOfPeople}}</td>
                        <td>{{dto.appointment.price}} din.</td>
                        <td v-if="dto.dateIsCorrect"><button type="button" class="btn btn-success" v-on:click="cancelAdvAppointment(dto)">Otkazi</button> </td>
                        <td v-else><p style="color:red;">Nije moguce otkazati 3 dana pre termina</p> </td>
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

        axios.all([axios.get('/cottageAppointments/getReservedCottAppointmentsByClient/' + this.activeUser.id),
        axios.get('/boatAppointments/getReservedBoatAppointmentsByClient/' + this.activeUser.id),
        axios.get('/fishingAppointments/getReservedAdvAppointmentsByClient/' + this.activeUser.id)])
        .then(axios.spread((...responses) => {
           this.cottageDtos = responses[0].data
           this.boatDtos = responses[1].data
           this.adventuresDtos = responses[2].data
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
        cancelCottAppointment: function(dto){
            axios
            .put('/cottageAppointments/cancelCottageAppointment/' + dto.appointment.id)
            .then(response=>{
                window.location.reload()
            })
            .catch(error=>{
                console.log("Greska.")	
                alert("Greska.")
                window.location.reload()
    
            })
        },
        cancelBoatAppointment: function(dto){
            axios
            .put('/boatAppointments/cancelBoatAppointment/' + dto.appointment.id)
            .then(response=>{
                window.location.reload()
            })
            .catch(error=>{
                console.log("Greska.")	
                alert("Greska.")
                window.location.reload()
    
            })
        },
        cancelAdvAppointment: function(dto){
            axios
            .put('/fishingAppointments/cancelAdventureAppointment/' + dto.appointment.id)
            .then(response=>{
                window.location.reload()
            })
            .catch(error=>{
                console.log("Greska.")	
                alert("Greska.")
                window.location.reload()
    
            })
        },
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
