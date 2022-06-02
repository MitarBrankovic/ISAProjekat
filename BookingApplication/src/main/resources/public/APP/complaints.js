Vue.component("Complaints", {
    data: function() {
        return {
            activeUser:"",
            complaints:null,
            answerClick:false,
            textAreaComplaint:"",
            clickedComplaints:[],

        }
    },
    template:`  
        <div style="margin-top: 30px;" v-if="activeUser.role=='admin'">
            <h2 class="flex title-div bigtitle" style="color: #5cb85c;">Sve žalbe</h2>
            <div v-for="c in complaints" class="list-group container">
                <div class="list-item">
                    <p>
                        <b>Klijent:</b> {{c.client.name}} {{c.client.surname}}<br>
                        <b>Sadržaj žalbe:</b> {{c.text}}<br>
                        <b>Ime vlasnika:</b> {{c.nameSurnameOwner}}<br>
                        <b>Naziv usluge:</b> {{c.entity}}<br>
                        <button class="btn btn-success" style="margin-top: 1%;" type="button" v-if="answerClick==false" v-on:click="addToClicked(c)">Otvori</button>
                        <br>
                    </p>

                    <div v-if="isClicked(c)">
                    <textarea class="form-control" v-model="textAreaComplaint" id="deleteArea" placeholder="Ovde napišite odgovor na žalbu..." rows="4" cols="70" required></textarea><br>

                        <button class="btn btn-success" type="button" v-on:click="answerComplaint(c)">Odgovori</button>
                        <button class="btn btn-danger" type="button" v-on:click="removeFromClicked(c)">Otkaži</button>
                    
                    </div>
                    <hr>
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
        .get('/appUser/getComplaints')
        .then(response=>{
            this.complaints = response.data
        })
        .catch(error=>{
            console.log("Greska.")	
            alert("Greska.")
            window.location.reload()

        })
	},
    methods:{
        answerComplaint:function(c){
            const answerComplaintDto = {
                complaint: c,
                text: this.textAreaComplaint,
            }
            if (this.textAreaComplaint.trim() != "") {
	            axios
	            .post('/admin/answerComplaint', answerComplaintDto,{
                headers: {
                  'Authorization': `Bearer ${localStorage.jwt.slice(1,-1)}`
                },})
	            .then(response=>{
	                this.complaints = response.data
	                Swal.fire({ icon: 'success', title: 'Odgovorili ste na žalbu !', showConfirmButton: false, timer: 1500 })
	            })
	            .catch(error=>{
	                console.log("Greska.")	
	                alert("Podaci su lose uneti.")
	                window.location.reload()
	
	            })
	         }
	         else
	         	Swal.fire({icon: 'error', title: 'Greška', text: 'Morate uneti odgovor na žalbu !'})

        },
        addToClicked:function(c){
            this.clickedComplaints.push(c)
        },
        removeFromClicked:function(c){
            var i = this.clickedComplaints.length
            while(i--){
                if(this.clickedComplaints[i].id == c.id){
                    this.clickedComplaints.splice(i,1)
                }
            }
        },
        isClicked:function(c){
            var exists = false
            for(var i = 0; i < this.clickedComplaints.length; i++){
                if(c.id == this.clickedComplaints[i].id){
                    exists = true
                    break
                }
            }
            return exists
        }
    }
});
