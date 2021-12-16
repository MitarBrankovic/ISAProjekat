Vue.component("ProfileCottage", {
    data: function() {
        return {
            activeUser: "",
            cottage : "",
			appointments : []
        }
    },
    template : ` 
    <div>
    <h1>{{this.cottage.name}}</h1>address
    <p>Adresa: {{this.cottage.address}}
    <br> Opis :{{this.cottage.description}}
    <br> Broj soba: {{this.cottage.roomsNum}}
    <br> Broj kreveta po sobi : {{this.cottage.bedsNum}}
    <br> Pravila : {{this.cottage.rules}}
    <br> Cenovnik i dodatne usluge:
    <br> {{this.cottage.priceList}}
    </p>
    <table class="table">
        <thead>
            <td>Datum i vreme pocetka rezervacije</td>
            <td>Trajanje</td>
            <td>Maksimalan broj osoba</td>
            <td>Dodatne usluge</td>
            <td>Cena</td>
            <td>Stanje</td>
        </thead>
        <tbody>
            <tr v-for="a in appointments">
            <td>{{a.appointmentStart}}</td>
            <td>{{a.duration}} dana</td>
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
            .put('/cottageAppointments/scheduleCottageAppointment/' + appointment.id + "/" + this.activeUser.id)
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
            	.get("cottages/getSelectedCottage/" + this.$route.query.id)
	            .then(response => (this.cottage = response.data));
		axios
            	.get("cottageAppointments/getAllQuickAppointments/" + this.$route.query.id)
	            .then(response => (this.appointments = response.data));
    },

});