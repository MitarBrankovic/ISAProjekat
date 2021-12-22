Vue.component("HistoryReservations", {
    data: function() {
        return {
            activeUser:"",
            cottagesButton:false,
            boatsButton:false,
            adventuresButton:true,
            cottages:[],
            boats:[],
            adventures:[],
            myRatedAdventures:[],
            upcomingAdventures:[]
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
                        <td v-if="!adventureIsRated(appointment) && !adventureIsUpcoming(appointment)"><button  type="submit" class="button" v-on:click="rateAdventure(appointment)">Oceni</button></td>
                        <td v-else-if="adventureIsRated(appointment)"><label>Ocenjeno</label></td>
                        <td v-else-if="adventureIsUpcoming(appointment)"><label>Nije jos zavrseno</label></td>
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
        axios.get('/fishingAppointments/getAllAdvAppointmentsByClient/' + this.activeUser.id),
        axios.get('/rating/getMyRatedAdventures/' + this.activeUser.email),
        axios.get('/fishingAppointments/getReservedAdvAppointmentsByClient/' + this.activeUser.id)])
        .then(axios.spread((...responses) => {
           this.cottages = responses[0].data
           this.boats = responses[1].data
           this.adventures = responses[2].data
           this.myRatedAdventures = responses[3].data
           this.upcomingAdventures = responses[4].data
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
        },
        adventureIsRated:function(appointment){
            var postoji = false
            for(var i = 0; i < this.myRatedAdventures.length; i++){
                if(this.activeUser.id == this.myRatedAdventures[i].client.id && appointment.fishingAdventure.id == this.myRatedAdventures[i].fishingAdventure.id){
                    postoji = true
                    break
                }
            }
            return postoji
        },
        adventureIsUpcoming:function(appointment){
            var postoji = false
            for(var i = 0; i < this.upcomingAdventures.length; i++){
                if(appointment.id == this.upcomingAdventures[i].appointment.id){
                    postoji = true
                    break
                }
            }
            return postoji
        },
        rateAdventure:function(appointment){
            (async () => {
                const { value: formValues } = await Swal.fire({
                    title: 'Ocenite ovu uslugu',
                    html:
                    '<label id="swalh" class="swal2-label" required>Komentar:</label>' +
                    '<input id="swal-input1" class="swal2-textarea" required>' +
                    '<form style="margin-left:5%;" class="rate centerIt" name="myForm" id="group">'+
                        '<input type="radio" id="star5" name="group" value="1" />' +
                        '<label for="star5" title="text">5 stars</label>' +
                        '<input type="radio" id="star4" name="group" value="2" />' +
                        '<label for="star4" title="text">4 stars</label>' +
                        '<input type="radio" id="star3" name="group" value="3" />' +
                        '<label for="star3" title="text">3 stars</label>' +
                        '<input type="radio" id="star2" name="group" value="4" />' +
                        '<label for="star2" title="text">2 stars</label>' +
                        '<input type="radio" id="star1" name="group" value="5" />' +
                        '<label for="star1" title="text">1 star</label>' +
                    '</form>',
                    focusConfirm: false,
                    preConfirm: () => {
                        if (!document.getElementById('swal-input1').value || !document.forms.myForm.group.value){
                            Swal.showValidationMessage('Popunite sva polja!')
                        }else{
                            return [
                                document.getElementById('swal-input1').value,
                                document.forms.myForm.group.value
                            ]
                        }   
                    }
                })
                
                if (formValues) {
                    const ratingAdvDto = {
                        fishingAdventure: appointment.fishingAdventure,
                        client: this.activeUser,
                        rating: formValues[1],
                        revision: formValues[0]
                    }
        
                    axios
                    .post('/rating/rateAdventure', ratingAdvDto)
                    .then(response=>{
                        window.location.reload()
                    })
                    .catch(error=>{
                        console.log("Greska.")	
                        alert("Podaci su lose uneti.")
                        window.location.reload()
        
                    })
                }

            })()




        }
    }
});
