Vue.component("ProfileBoat", {
    data: function() {
        return {
            activeUser: "",
            boat : "",
			appointments : [],
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

    </p>
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
        }
    },
    mounted(){
        this.activeUser = JSON.parse(localStorage.getItem('activeUser'))
        axios
            .get("boats/getSelectedBoat/" + this.$route.query.id)
            .then(response => (this.boat = response.data));
		axios
            .get("boatAppointments/getAllQuickAppointments/" + this.$route.query.id)
            .then(response => (this.appointments = response.data));
    },

});