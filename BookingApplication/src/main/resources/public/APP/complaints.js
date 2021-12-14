Vue.component("Complaints", {
    data: function() {
        return {
            activeUser:"",
            complaints:null,
            answerClick:false,
            textAreaComplaint:"",
            clickedComplaints:[]
        }
    },
    template:`  
        <div style="margin-top: 30px;" v-if="activeUser.role=='admin'">
            <h2 class="flex title-div bigtitle">All complaints</h2>
            <div v-for="c in complaints" class="list-group container">
                <div class="list-item">
                    <p>
                        <b>User:</b> {{c.appUser.name}} {{c.appUser.surname}}<br>
                        <b>Text of complaint:</b> {{c.text}}<br>
                        <b>Owner:</b> {{c.cottage.cottageOwner.name}} {{c.cottage.cottageOwner.surname}}<br>
                        <button class="btn btn-success" type="button" v-if="answerClick==false" v-on:click="addToClicked(c)">Open</button>
                        <br>
                    </p>

                    <div v-if="isClicked(c)">
                    <textarea v-model="textAreaComplaint" id="deleteArea" placeholder="Write a response to the complaint here..." rows="4" cols="50"></textarea><br>

                        <button class="btn btn-success" type="button" v-on:click="answerComplaint(c)">Answer</button>
                        <button class="btn btn-danger" type="button" v-on:click="removeFromClicked(c)">Cancel</button>
                    
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
            axios
            .post('/admin/answerComplaint', answerComplaintDto)
            .then(response=>{
                this.complaints = response.data
            })
            .catch(error=>{
                console.log("Greska.")	
                alert("Podaci su lose uneti.")
                window.location.reload()

            })

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
