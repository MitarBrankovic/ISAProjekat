Vue.component("HistoryReservations", {
    data: function() {
        return {
            activeUser:"",
            cottagesButton:true,
            boatsButton:false,
            adventuresButton:false,
            cottages:[],
            boats:[],
            adventures:[],
            myRatedAdventures:[],
            upcomingAdventures:[],
            myRatedCottages:[],
            upcomingCottages:[],
            myRatedBoats:[],
            upcomingBoats:[],

            myRatedCottageOwners:[],

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
                <SearchCottAppointments style="margin-top: 30px;" id="search" @clicked="searchCottAppointments"></SearchCottAppointments><br><br>

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
                        <td>Oceni vlasnika</td>
                    </tr>
                    </thead>
                    <tbody>
                        <tr v-for="appointment in cottages">
                        <td><a class="linkAppointment" v-on:click="showCottageInformation(appointment.cottage.id)">{{appointment.cottage.name}}</a></td>
                        <td>{{appointment.appointmentStart}}</td>
                        <td>{{appointment.duration}} h</td>
                        <td>{{appointment.maxAmountOfPeople}}</td>
                        <td>{{appointment.price}} din.</td>
                        <td v-if="!cottageIsRated(appointment) && !cottageIsUpcoming(appointment)"><button  type="submit" class="button" v-on:click="rateCottage(appointment)">Oceni</button></td>
                        <td v-else-if="cottageIsRated(appointment)"><label style="color:green;">Ocenjeno</label></td>
                        <td v-else-if="cottageIsUpcoming(appointment)"><label>Nije jos zavrseno</label></td>
                        <td v-if="!cottageOwnerIsRated(appointment) && !cottageIsUpcoming(appointment)"><button  type="submit" class="button" v-on:click="rateCottageOwner(appointment)">Oceni</button></td>
                        <td v-else-if="cottageOwnerIsRated(appointment)"><label style="color:green;">Ocenjeno</label></td>
                        <td v-else-if="cottageIsUpcoming(appointment)"><label>Nije jos zavrseno</label></td>
                        </tr>
                    </tbody>
                </table>
                </div>
            </div>

            <div v-else-if="boatsButton">
                <h2 class="flex title-div bigtitle">Istorija rezervacija brodova</h2>
                <SearchBoatAppointments style="margin-top: 30px;" id="search" @clicked="searchBoatAppointments"></SearchBoatAppointments><br><br>

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
                        <td v-if="!boatIsRated(appointment) && !boatIsUpcoming(appointment)"><button  type="submit" class="button" v-on:click="rateBoat(appointment)">Oceni</button></td>
                        <td v-else-if="boatIsRated(appointment)"><label style="color:green;">Ocenjeno</label></td>
                        <td v-else-if="boatIsUpcoming(appointment)"><label>Nije jos zavrseno</label></td>
                        </tr>
                    </tbody>
                </table>
                </div>
            </div>

            <div v-else-if="adventuresButton">
                <h2 class="flex title-div bigtitle">Istorija rezervacija avantura</h2>
                <SearchAdvAppointments style="margin-top: 30px;" id="search" @clicked="searchAdvAppointments"></SearchAdvAppointments><br><br>

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
                        <td v-else-if="adventureIsRated(appointment)"><label style="color:green;">Ocenjeno</label></td>
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
        axios.get('/fishingAppointments/getReservedAdvAppointmentsByClient/' + this.activeUser.id),
        axios.get('/rating/getMyRatedCottages/' + this.activeUser.email),
        axios.get('/cottageAppointments/getReservedCottAppointmentsByClient/' + this.activeUser.id),
        axios.get('/rating/getMyRatedBoats/' + this.activeUser.email),
        axios.get('/boatAppointments/getReservedBoatAppointmentsByClient/' + this.activeUser.id),
        axios.get('/rating/getMyRatedCottOwners/' + this.activeUser.email)])
        .then(axios.spread((...responses) => {
           this.cottages = responses[0].data
           this.boats = responses[1].data
           this.adventures = responses[2].data
           this.myRatedAdventures = responses[3].data
           this.upcomingAdventures = responses[4].data
           this.myRatedCottages = responses[5].data
           this.upcomingCottages = responses[6].data
           this.myRatedBoats = responses[7].data
           this.upcomingBoats = responses[8].data
           this.myRatedCottageOwners = responses[9].data
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
                    '<form style="margin-left:5%;" class="rate centerIt starInvert" name="myForm" id="group">'+
                        '<input type="radio" id="star5" name="group" value="5" />' +
                        '<label for="star5" title="text">5 stars</label>' +
                        '<input type="radio" id="star4" name="group" value="4" />' +
                        '<label for="star4" title="text">4 stars</label>' +
                        '<input type="radio" id="star3" name="group" value="3" />' +
                        '<label for="star3" title="text">3 stars</label>' +
                        '<input type="radio" id="star2" name="group" value="2" />' +
                        '<label for="star2" title="text">2 stars</label>' +
                        '<input type="radio" id="star1" name="group" value="1" />' +
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
                    .post('/rating/rateAdventure', ratingAdvDto,{
                        headers: {
                          'Authorization': `Bearer ${localStorage.jwt.slice(1,-1)}`
                        },})
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
        },
        cottageIsRated:function(appointment){
            var postoji = false
            for(var i = 0; i < this.myRatedCottages.length; i++){
                if(this.activeUser.id == this.myRatedCottages[i].client.id && appointment.cottage.id == this.myRatedCottages[i].cottage.id){
                    postoji = true
                    break
                }
            }
            return postoji
        },
        cottageIsUpcoming:function(appointment){
            var postoji = false
            for(var i = 0; i < this.upcomingCottages.length; i++){
                if(appointment.id == this.upcomingCottages[i].appointment.id){
                    postoji = true
                    break
                }
            }
            return postoji
        },
        rateCottage:function(appointment){
            (async () => {
                const { value: formValues } = await Swal.fire({
                    title: 'Ocenite ovu vikendicu',
                    html:
                    '<label id="swalh" class="swal2-label" required>Komentar:</label>' +
                    '<input id="swal-input1" class="swal2-textarea" required>' +
                    '<form style="margin-left:5%;" class="rate centerIt starInvert" name="myForm" id="group">'+
                        '<input type="radio" id="star5" name="group" value="5" />' +
                        '<label for="star5" title="text">5 stars</label>' +
                        '<input type="radio" id="star4" name="group" value="4" />' +
                        '<label for="star4" title="text">4 stars</label>' +
                        '<input type="radio" id="star3" name="group" value="3" />' +
                        '<label for="star3" title="text">3 stars</label>' +
                        '<input type="radio" id="star2" name="group" value="2" />' +
                        '<label for="star2" title="text">2 stars</label>' +
                        '<input type="radio" id="star1" name="group" value="1" />' +
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
                    const ratingCottDto = {
                        cottage: appointment.cottage,
                        client: this.activeUser,
                        rating: formValues[1],
                        revision: formValues[0]
                    }
        
                    axios
                    .post('/rating/rateCottage', ratingCottDto,{
                        headers: {
                          'Authorization': `Bearer ${localStorage.jwt.slice(1,-1)}`
                        },})
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
        },
        boatIsRated:function(appointment){
            var postoji = false
            for(var i = 0; i < this.myRatedBoats.length; i++){
                if(this.activeUser.id == this.myRatedBoats[i].client.id && appointment.boat.id == this.myRatedBoats[i].boat.id){
                    postoji = true
                    break
                }
            }
            return postoji
        },
        boatIsUpcoming:function(appointment){
            var postoji = false
            for(var i = 0; i < this.upcomingBoats.length; i++){
                if(appointment.id == this.upcomingBoats[i].appointment.id){
                    postoji = true
                    break
                }
            }
            return postoji
        },
        rateBoat:function(appointment){
            (async () => {
                const { value: formValues } = await Swal.fire({
                    title: 'Ocenite ovaj brod',
                    html:
                    '<label id="swalh" class="swal2-label" required>Komentar:</label>' +
                    '<input id="swal-input1" class="swal2-textarea" required>' +
                    '<form style="margin-left:5%;" class="rate centerIt starInvert" name="myForm" id="group">'+
                        '<input type="radio" id="star5" name="group" value="5" />' +
                        '<label for="star5" title="text">5 stars</label>' +
                        '<input type="radio" id="star4" name="group" value="4" />' +
                        '<label for="star4" title="text">4 stars</label>' +
                        '<input type="radio" id="star3" name="group" value="3" />' +
                        '<label for="star3" title="text">3 stars</label>' +
                        '<input type="radio" id="star2" name="group" value="2" />' +
                        '<label for="star2" title="text">2 stars</label>' +
                        '<input type="radio" id="star1" name="group" value="1" />' +
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
                    const ratingBoatDto = {
                        boat: appointment.boat,
                        client: this.activeUser,
                        rating: formValues[1],
                        revision: formValues[0]
                    }
        
                    axios
                    .post('/rating/rateBoat', ratingBoatDto,{
                        headers: {
                          'Authorization': `Bearer ${localStorage.jwt.slice(1,-1)}`
                        },})
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
        },

        cottageOwnerIsRated:function(appointment){
            var postoji = false
            for(var i = 0; i < this.myRatedCottageOwners.length; i++){
                if(this.activeUser.id == this.myRatedCottageOwners[i].client.id && appointment.cottage.cottageOwner.id == this.myRatedCottageOwners[i].cottageOwner.id){
                    postoji = true
                    break
                }
            }
            return postoji
        },
        rateCottageOwner:function(appointment){
            (async () => {
                const { value: formValues } = await Swal.fire({
                    title: 'Ocenite ovog vlasnika',
                    html:
                    '<label id="swalh" class="swal2-label" required>Komentar:</label>' +
                    '<input id="swal-input1" class="swal2-textarea" required>' +
                    '<form style="margin-left:5%;" class="rate centerIt starInvert" name="myForm" id="group">'+
                        '<input type="radio" id="star5" name="group" value="5" />' +
                        '<label for="star5" title="text">5 stars</label>' +
                        '<input type="radio" id="star4" name="group" value="4" />' +
                        '<label for="star4" title="text">4 stars</label>' +
                        '<input type="radio" id="star3" name="group" value="3" />' +
                        '<label for="star3" title="text">3 stars</label>' +
                        '<input type="radio" id="star2" name="group" value="2" />' +
                        '<label for="star2" title="text">2 stars</label>' +
                        '<input type="radio" id="star1" name="group" value="1" />' +
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
                    const ratingCottOwnerDto = {
                        cottageOwner: appointment.cottage.cottageOwner,
                        client: this.activeUser,
                        rating: formValues[1],
                        revision: formValues[0]
                    }
        
                    axios
                    .post('/rating/rateCottageOwner', ratingCottOwnerDto,{
                        headers: {
                          'Authorization': `Bearer ${localStorage.jwt.slice(1,-1)}`
                        },})
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
