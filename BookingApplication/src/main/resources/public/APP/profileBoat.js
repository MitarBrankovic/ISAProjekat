Vue.component("ProfileBoat", {
    data: function() {
        return {
            activeUser: "",
            boat : "",
			appointments : [],
            subscibedBoats:[]
        }
    },
    template : ` 
    <div>
    <h1>{{boat.name}}</h1>
    <p>Adress: {{boat.address}}
    <br> Type of boat : {{boat.boatType}}
    <br> Length of boat: {{boat.length}}
    <br> Engine number : {{boat.engineNumber}}
    <br> Engine power : {{boat.enginePower}}
    <br> Maximum boat speed: {{boat.maxSpeed}}
    <br> Navigation equipment : {{boat.navigationEquipment}}
    <br> Description : {{boat.description}}
    <br> Capacity :{{boat.capacity}}
    <br> Rules : {{boat.rules}}
    <br> Fising equipment : {{boat.fishingEquipment}}
    <br> Price list : {{boat.priceList}}
    <br> Cancelation terms : {{boat.cancellationTerms}}
    </p><br>
    <button v-if="activeUser.role == 'client' && !exist()" type="submit" class="button" v-on:click="subscribe()">Pretplati se</button>
    <button v-if="activeUser.role == 'client' && exist()" type="submit" class="btn btn-danger" v-on:click="unsubscribe()">Odjavi se</button>

    <br><br><hr>

    <table class="table">
        <thead>
            <td>Datum i vreme pocetka rezervacije</td>
            <td>Trajanje</td>
            <td>Maksimalan broj osoba</td>
            <td>Dodatne usluge</td>
            <td>Cena</td>
            <td></td>
        </thead>
        <tbody>
            <tr v-for="a in appointments">
            <td>{{a.appointmentStart}}</td>
            <td>{{a.duration}} sati</td>
            <td>{{a.maxAmountOfPeople}}</td>
            <td>{{a.extraNotes}}</td>
            <td>{{a.price}}</td>
            <td v-if="a.client==null"><button v-if="activeUser.role == 'client'" type="button" class="btn btn-success" v-on:click="scheduleAppointment(a)">Zakazi</button> </td>
            <td v-else><p style="color:red;">Zakazano</p> </td>
            </tr>
        </tbody>
    </table>

</div> 
    	`
    	,
    methods: {
        scheduleAppointment:function(appointment){
            axios
            .put('/boatAppointments/scheduleBoatAppointment/' + appointment.id + "/" + this.activeUser.id)
            .then(response=>{
                window.location.reload()
            })
            .catch(error=>{
                console.log("Greska.")	
                alert("Podaci su lose uneti.")
                window.location.reload()

            })
        },
        subscribe:function(){
            const subscibedBoat = {
                id: 0,
                boat: this.boat,
                client: this.activeUser.id
            }
            axios
            .post('/subscribe/subscribeBoat', subscibedBoat)
            .then(response=>{
                window.location.reload()
            })
            .catch(error=>{
                console.log("Greska.")	
                alert("Podaci su lose uneti.")
                window.location.reload()
            })
        },
        unsubscribe:function(){
            const subscibedBoat = {
                id: 0,
                boat: this.boat,
                client: this.activeUser.id
            }
            axios
            .post('/subscribe/unsubscribeBoat', subscibedBoat)
            .then(response=>{
                window.location.reload()
            })
            .catch(error=>{
                console.log("Greska.")	
                alert("Podaci su lose uneti.")
                window.location.reload()
            })
        },
        exist:function(){
            var postoji = false
            for(var i = 0; i < this.subscibedBoats.length; i++){
                if(this.activeUser.id == this.subscibedBoats[i].client && this.boat.id == this.subscibedBoats[i].boat.id){
                    postoji = true
                    break
                }
            }
            return postoji
        }
    },
    mounted(){
        this.activeUser = JSON.parse(localStorage.getItem('activeUser'))

        axios.all([
            axios.get("boats/getSelectedBoat/" + this.$route.query.id), 
            axios.get("boatAppointments/getAllQuickAppointments/" + this.$route.query.id),
            axios.get('/subscribe/getAllSubscibedBoats')]).then(axios.spread((...responses) => {
            this.boat = responses[0].data
            this.appointments = responses[1].data
            this.subscibedBoats = responses[2].data
        }))
    },

});