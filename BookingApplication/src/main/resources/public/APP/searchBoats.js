Vue.component("SearchBoats",{
    data:function(){
        return{
            search:{
                nameAsc:false,
                nameDesc:false,
                addressAsc:false,
                addressDesc:false,
                name:"",
                address:"",

            }
        }
    },
    template:`
        <div>
            <h3 id="search3">Search boats</h3>
            <input type="text" v-model="search.name" placeholder="name"/>
            <input type="text" v-model="search.address" placeholder="address"/>
            <button type="button" v-on:click="searchFun()" class="btn btn-sm btn-primary bi bi-search">Search</button>
            <button type="button" v-on:click="cancelSearch()" class="btn btn-sm btn-outline-danger bi bi-x">Cancel search</button>
            <br>
        
            <div>
                <button class="bi bi-arrow-up btn btn-info" type="button" v-on:click="nameAscFun()">Name ascending</button>
                <button class="bi bi-arrow-down btn btn-info" type="button" v-on:click="nameDescFun()">Name descending</button>
                <button class="bi bi-arrow-up btn btn-info" type="button" v-on:click="addressAscFun()">Address ascending</button>
                <button class="bi bi-arrow-down btn btn-info" type="button" v-on:click="addressDescFun()">Address descending</button>
            </div>
        
        
        
        
        </div>                
    `,
    methods:{
        searchFun:function(){
            this.$emit('clicked',this.search)
        },
        nameAscFun: function(){
            this.search.nameAsc = true
            this.search.nameDesc = false
            this.search.addressAsc = false
            this.search.addressDesc = false
            console.log("Klik!")
            this.$emit('clicked', this.search)
        },
        nameDescFun: function(){
            this.search.nameAsc = false
            this.search.nameDesc = true
            this.search.addressAsc = false
            this.search.addressDesc = false
            console.log("Klik!")
            this.$emit('clicked', this.search)
        },
        addressAscFun: function(){
            this.search.nameAsc = false
            this.search.nameDesc = false
            this.search.addressAsc = true
            this.search.addressDesc = false
            console.log("Klik!")
            this.$emit('clicked', this.search)
        },
        addressDescFun: function(){
            this.search.nameAsc = false
            this.search.nameDesc = false
            this.search.addressAsc = false
            this.search.addressDesc = true
            console.log("Klik!")
            this.$emit('clicked', this.search)
        },
        cancelSearch: function(){
            this.search.nameAsc = false
            this.search.nameDesc = false
            this.search.addressAsc = false
            this.search.addressDesc = false      
            this.search.name="",
            this.search.address="",
            console.log("Klik!")
            this.$emit('clicked', this.search)
        }
    }
})