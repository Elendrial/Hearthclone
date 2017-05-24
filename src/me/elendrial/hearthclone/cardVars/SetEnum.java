package me.elendrial.hearthclone.cardVars;

import com.google.gson.annotations.SerializedName;

public enum SetEnum {
	@SerializedName(value = "BASIC", alternate={"basic"}) BASIC,
	@SerializedName(value = "CLASSIC", alternate={"classic", "core", "CORE"}) CLASSIC,
	@SerializedName(value = "HALLOFFAME", alternate={"HOF"}) HALLOFFAME,
	@SerializedName(value = "NAXXRAMAS", alternate={"naxxramas", "NAXX", "naxx"}) NAXXRAMAS,
	@SerializedName(value = "GOBLINSVSGNOMES", alternate={"gvg", "goblinsvsgnomes", "GVG"}) GOBLINSVSGNOMES,
	@SerializedName(value = "LEAGUEOFEXPLORERS", alternate={"LOE", "loe", "LEAGUEOFEXPLORERS"}) LEAGUEOFEXPLORERS,
	@SerializedName(value = "THEGRANDTOURNAMENT", alternate={"TGT", "tgt", "thegrandtournament"}) THEGRANDTOURNAMENT,
	@SerializedName(value = "TAVERNBRAWL", alternate={"tb", "TB", "tavernbrawl"}) TAVERNBRAWL,
	@SerializedName(value = "BLACKROCKMOUNTAIN", alternate={"BRM", "brm", "bm", "BM", "blackrockmountain"}) BLACKROCKMOUNTAIN,
	@SerializedName(value = "UNGORO", alternate={"ungoro", "jtu", "JTU", "JOURNEYTOUNGORO" , "journeytoungoro"}) UNGORO,
	@SerializedName(value = "GADGETZAN", alternate={"gadgetzan", "msog", "MSOG", "MEANSTREETSOFGADGETZAN", "meanstreetsofgadgetzan"}) GADGETZAN,
	@SerializedName(value = "WHISPERS", alternate={"whispers", "wotog", "WOTOG", "WHISPERSOFTHEOLDGODS", "whispersoftheoldgods"}) WHISPERS,
	@SerializedName(value = "KARAZHAN", alternate={"karazhan", "onik", "ONIK"}) KARAZHAN, 
	@SerializedName(value = "UNCOLLECTABLE", alternate={"uncollectable"}) UNCOLLECTABLE,
	@SerializedName(value = "CREDITS", alternate={"credits"}) CREDITS,
	CUSTOM;
}
