package dev.samdahles.armorstand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.util.EulerAngle;
import org.json.JSONObject;
import org.bukkit.command.ConsoleCommandSender;

public class Pose {
	public static enum BodyPart {
		BODY,
		HEAD,
		LEFT_ARM,
		LEFT_LEG,
		RIGHT_ARM,
		RIGHT_LEG
	}

	public static Pose nextPose(Pose initialPose) {
		return Pose.DEFAULT_POSES.get(initialPose.getIndex() + 1);
	}
	
	public static final List<Pose> DEFAULT_POSES = Pose.DEFAULT_POSES();

	private static final List<Pose> DEFAULT_POSES() {
		List<Pose> defaultPoses = new ArrayList<Pose>();
		defaultPoses.add(new Pose("{Pose:{Head:[354f,0f,0f],LeftLeg:[9f,0f,0f],RightLeg:[347f,0f,0f],LeftArm:[282f,0f,0f],RightArm:[324f,0f,0f]}}"));
		defaultPoses.add(new Pose("{Pose:{Head:[18f,0f,0f],LeftArm:[311f,46f,0f]}}"));
		return defaultPoses;
	}
	
	private Map<BodyPart, EulerAngle> poses = new HashMap<BodyPart, EulerAngle>();
	
	public Pose(ArmorStand armorStand) {
		poses.put(BodyPart.BODY, armorStand.getBodyPose());
		poses.put(BodyPart.HEAD, armorStand.getHeadPose());
		poses.put(BodyPart.LEFT_ARM, armorStand.getLeftArmPose());
		poses.put(BodyPart.LEFT_LEG, armorStand.getLeftLegPose());
		poses.put(BodyPart.RIGHT_ARM, armorStand.getRightArmPose());
		poses.put(BodyPart.RIGHT_LEG, armorStand.getRightLegPose());
	}
	
	public Pose(EulerAngle bodyAngle, EulerAngle headAngle, EulerAngle leftArmAngle, EulerAngle leftLegAngle, EulerAngle rightArmAngle, EulerAngle rightLegAngle) {
		poses.put(BodyPart.BODY, bodyAngle);
		poses.put(BodyPart.HEAD, headAngle);
		poses.put(BodyPart.LEFT_ARM, leftArmAngle);
		poses.put(BodyPart.LEFT_LEG, leftLegAngle);
		poses.put(BodyPart.RIGHT_ARM, rightArmAngle);
		poses.put(BodyPart.RIGHT_LEG, rightLegAngle);
	}
	
	public Pose(String NBT) {
		World overworld = Bukkit.getWorld("world");
		ArmorStand armorStand = (ArmorStand) overworld.spawnEntity(new Location(overworld, 10^3, 0, 0), EntityType.ARMOR_STAND);
		String armorStandUUID = armorStand.getUniqueId().toString();
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "/execute as " + armorStandUUID + " run entitydata @s " + NBT);
		Core.logger.info("execute as " + armorStandUUID + " run entitydata @s " + NBT);
		poses.put(BodyPart.BODY, armorStand.getBodyPose());
		poses.put(BodyPart.HEAD, armorStand.getHeadPose());
		poses.put(BodyPart.LEFT_ARM, armorStand.getLeftArmPose());
		poses.put(BodyPart.LEFT_LEG, armorStand.getLeftLegPose());
		poses.put(BodyPart.RIGHT_ARM, armorStand.getRightArmPose());
		poses.put(BodyPart.RIGHT_LEG, armorStand.getRightLegPose());
		armorStand.remove();
	}
	
	public Map<BodyPart, EulerAngle> toMap() {
		return this.poses;
	}
	
	public EulerAngle get(BodyPart bodyPart) {
		return this.poses.get(bodyPart);
	}
	
	public void setPose(BodyPart bodyPart, EulerAngle eulerAngle) {
		this.poses.put(bodyPart, eulerAngle);
	}
	
	public void applyTo(ArmorStand armorStand) {
		armorStand.setBodyPose(this.poses.get(BodyPart.BODY));
		armorStand.setHeadPose(this.poses.get(BodyPart.HEAD));
		armorStand.setLeftArmPose(this.poses.get(BodyPart.LEFT_ARM));
		armorStand.setLeftLegPose(this.poses.get(BodyPart.LEFT_LEG));
		armorStand.setRightArmPose(this.poses.get(BodyPart.RIGHT_ARM));
		armorStand.setRightLegPose(this.poses.get(BodyPart.RIGHT_LEG));
	}
	
	
	public int getIndex() {
		for(int i=0; i<Pose.DEFAULT_POSES.size(); i++) {
			if(Pose.DEFAULT_POSES.get(i).toMap().equals(this.toMap())) {
				return i;
			}
		}
		return -1;
	}
	
}
