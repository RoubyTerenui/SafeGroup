package com.project.safegroup.GroupSelection;

public class GroupSelectionData {

        private String name; //name of the group
        private boolean selected; //if the user is actually selecting the group
        private boolean favorite; //will be implemented
        private String Id; //id of the group for the server
        private boolean party;

        public GroupSelectionData(String name, boolean selected, boolean favorite,String id,boolean party ) {
            this.name=name;
            this.selected=selected;
            this.favorite=favorite;
            this.party=party;
            this.Id=id;
        }

        public String getName() {
            return name;
        }
        public String getId() {
        return Id;
    }

        public boolean isSelected() {
            return selected;
        }

        public boolean isFavorite() {
            return favorite;
        }
        public boolean isParty() {
        return party;
    }
        public Boolean select(){
            this.selected=!(this.selected);
            return this.selected;
        }


}
