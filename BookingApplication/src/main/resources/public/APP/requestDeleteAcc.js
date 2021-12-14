Vue.component("RequestDeleteAcc", {
    data: function() {
        return {
            activeUser:"",
            requests:null
        }
    },
    template:`  
        <div style="margin-top: 30px;" v-if="activeUser.role=='admin'">
            <h2 class="flex title-div bigtitle">All requests</h2>
            <div v-for="r in requests" class="list-group container">
                <div class="list-item">
                    <p>
                        <b>User Id:</b> {{r.appUserId}}<br>
                        <b>Text of request:</b> {{r.text}}<br>
						<button class="btn btn-success" type="button" v-on:click="acceptRequest(r)">Accept</button>
						<button class="btn btn-danger" type="button" v-on:click="declineRequest(r)">Decline</button>
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
            .post('/admin/acceptRequest', r)
            .then(response=>{
                this.requests = response.data
            })
        },
        declineRequest:function(r){
            axios
            .post('/admin/declineRequest', r)
            .then(response=>{
                this.requests = response.data
            })
        }


    }
});
