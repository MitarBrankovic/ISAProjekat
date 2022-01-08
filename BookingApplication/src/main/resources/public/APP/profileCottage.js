Vue.component("ProfileCottage", {
    data: function() {
        return {
            activeUser: "",
            cottage : "",
			appointments : [],
            subscibedCottages:[]
        }
    },
    template : ` 
    <div>
    <h1>{{this.cottage.name}}</h1>
    <p>Adresa: {{this.cottage.address}}
    <br> Opis :{{this.cottage.description}}
    <br> Broj soba: {{this.cottage.roomsNum}}
    <br> Broj kreveta po sobi : {{this.cottage.bedsNum}}
    <br> Pravila : {{this.cottage.rules}}
    <br> Cenovnik i dodatne usluge:
    <br> {{this.cottage.priceList}}
    </p><br>
    <button v-if="activeUser.role == 'client' && !exist()" type="submit" class="button" v-on:click="subscribe()">Pretplati se</button>
    <button v-if="activeUser.role == 'client' && exist()" type="submit" class="btn btn-danger" v-on:click="unsubscribe()">Odjavi se</button>

    <br><br><hr>
    <div class="container-fluid">
    <table class="table table-success table-striped table-sm table-bordered">
        <thead>
        <tr>
            <td>Datum i vreme pocetka rezervacije</td>
            <td>Trajanje</td>
            <td>Maksimalan broj osoba</td>
            <td>Dodatne usluge</td>
            <td>Cena</td>
            <td>Stanje</td>
        </tr>
        </thead>
        <tbody>
            <tr v-for="a in appointments">
            <td>{{a.appointmentStart}}</td>
            <td>{{a.duration}} dana</td>
            <td>{{a.maxAmountOfPeople}}</td>
            <td>{{a.extraNotes}}</td>
            <td>{{a.price}}</td>
            <td v-if="a.client==null"><button id="scheduleButton" v-if="checkUserandPenalties()" type="button" class="btn btn-success" v-on:click="scheduleAppointment(a)" disabled>Zakazi</button> </td>
            <td v-else><p style="color:red;">Zakazano</p> </td>
            </tr>
        </tbody>
    </table>
    </div>

</div>  
    	`
    	,
    methods: {
        scheduleAppointment:function(appointment){
            axios
            .put('/cottageAppointments/scheduleCottageAppointment/' + appointment.id + "/" + this.activeUser.id, {},{
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
        },
        checkUserandPenalties(){
            if(this.activeUser.role == 'client'){
                let num = 0
                axios
                .get('/reports/findAllReportsByClient/' + this.activeUser.id)
                .then(response=>{
                    num = response.data.cottageReports.length + response.data.boatReports.length + response.data.fishingReports.length;
                    if(num < 3)
                        document.getElementById("scheduleButton").disabled = false
                    else
                        document.getElementById("scheduleButton").disabled = true
                })
                .catch(error=>{
                    console.log("Greska.")	
                    alert("Podaci su lose uneti.")
                })
                return true;
            }else return false;

        },
        subscribe:function(){
            const subscribeCottage = {
                id: 0,
                cottage: this.cottage,
                client: this.activeUser
            }
            axios
            .post('/subscribe/subscribeCottage', subscribeCottage,{
                headers: {
                  'Authorization': `Bearer ${localStorage.jwt.slice(1,-1)}`
                },})
            .then(response=>{
                window.location.reload()
            })
            .catch(error=>{
                console.log("Greska.")	
                alert("Podaci su lose uneti.")
                //window.location.reload()
            })
        },
        unsubscribe:function(){
            const subscribeCottage = {
                id: 0,
                cottage: this.cottage,
                client: this.activeUser
            }
            axios
            .post('/subscribe/unsubscribeCottage', subscribeCottage,{
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
        },
        exist:function(){
            var postoji = false
            for(var i = 0; i < this.subscibedCottages.length; i++){
                if(this.activeUser.id == this.subscibedCottages[i].client.id && this.cottage.id == this.subscibedCottages[i].cottage.id){
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
            axios.get("cottages/getSelectedCottage/" + this.$route.query.id), 
            axios.get("cottageAppointments/getAllQuickAppointments/" + this.$route.query.id),
            axios.get('/subscribe/getAllSubscibedCottages')]).then(axios.spread((...responses) => {
           this.cottage = responses[0].data
           this.appointments = responses[1].data
           this.subscibedCottages = responses[2].data
       }))
    },

});