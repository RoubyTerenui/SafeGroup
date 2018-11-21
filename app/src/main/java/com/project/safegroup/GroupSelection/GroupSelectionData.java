package com.project.safegroup.GroupSelection;

public class GroupSelectionData {

        String name; //name of the group
        Boolean selected; //if the user is actually selecting the group
        Boolean favorite; //will be implemented
        String Id; //id of the group for the server

        public GroupSelectionData(String name, Boolean selected, Boolean favorite,String Id ) {
            this.name=name;
            this.selected=selected;
            this.favorite=favorite;
            this.Id=Id;
        }

        public String getName() {
            return name;
        }
        public String getId() {
        return Id;
    }

        public Boolean isSelected() {
            return selected;
        }

        public Boolean isFavorite() {
            return favorite;
        }
        public Boolean select(){
            this.selected=!(this.selected);
            return this.selected;
        }


}
