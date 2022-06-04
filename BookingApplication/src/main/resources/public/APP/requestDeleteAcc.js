Vue.component("RequestDeleteAcc", {
    data: function() {
        return {
            activeUser:"",
            requests:null
        }
    },
    template:`  
        <div style="margin-top: 30px;" v-if="activeUser.role=='admin'">
            <h2 class="flex title-div bigtitle" style="color: #5cb85c;">Svi zahtevi za brisanje naloga</h2>
            <div v-for="r in requests" class="list-group container">
                <div class="list-item">
                    <p>
                        <b>Korisnik:</b> {{r.appUserName}}<br>
                        <b>Tip korisnika:</b> {{r.appUserType}}<br>
                        <b>Tekst zahteva:</b> {{r.text}}<br>
						<button style="margin-top: 1%; margin-right: 1%" class="btn btn-success" type="button" v-on:click="acceptRequest(r)">Prihvati</button>
						<button style="margin-top: 1%; margin-left: 1%" class="btn btn-danger" type="button" v-on:click="declineRequest(r)">Odbij</button>
                        <hr>
                    </p>
                </div>
            </div>
        </div>
    `       
        ,
    mounted(){
        this.activeUser = JSON.parse(localStorage.getItem('activeUser'))
        if(this.activeUser.role != 'admin')
        this.$router.push('/')
        axios
        .get('/appUser/getRequests')
        .then(response=>{
            //window.location.reload()
            this.requests = response.data
        })
        .catch(error=>{
            console.log("Greska.")	
            alert("Podaci su lose uneti.")
            window.location.reload()

        })
	},
    methods:{
        acceptRequest:function(r){
            axios
            .post('/admin/acceptRequest', r, {
                headers: {
                  'Authorization': `Bearer ${localStorage.jwt.slice(1,-1)}`
                },})
            .then(response=>{
                this.requests = response.data
            })
            .catch(error=>{
            Swal.fire({ icon: 'info', title: 'Zahtev je vec obradjen od strane drugog admina.', showConfirmButton: false, timer: 5000 })
            window.location.reload()
        })
        },
        declineRequest:function(r){
            axios
            .post('/admin/declineRequest', r, {
                headers: {
                  'Authorization': `Bearer ${localStorage.jwt.slice(1,-1)}`
                },})
            .then(response=>{
                this.requests = response.data
            })
            .catch(error=>{
            Swal.fire({ icon: 'info', title: 'Zahtev je vec obradjen od strane drugog admina.', showConfirmButton: false, timer: 5000 })
            window.location.reload()
        })
        }


    }
});
