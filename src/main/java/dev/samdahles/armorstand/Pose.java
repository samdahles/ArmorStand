package dev.samdahles.armorstand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.entity.ArmorStand;
import org.bukkit.util.EulerAngle;

public class Pose {
	public static enum BodyPart {
		BODY,
		HEAD,
		LEFT_ARM,
		LEFT_LEG,
		RIGHT_ARM,
		RIGHT_LEG
	}

	public static final List<Pose> getDefault() {
		List<Pose> defaultPoses = new ArrayList<Pose>();
		//{Pose:{Head:[354f,0f,0f],LeftLeg:[9f,0f,0f],RightLeg:[347f,0f,0f],LeftArm:[282f,0f,0f],RightArm:[324f,0f,0f]}}
		//{Pose:{Head:[18f,0f,0f],LeftArm:[311f,46f,0f]}}
		
		defaultPoses.add(new Pose(_(), _(354), _(9), _(347), _(282), _(324)));
		defaultPoses.add(new Pose(_(), _(18), _(), _(), _(311, 46), _()));
		defaultPoses.add(new Pose(_(), _(0,35), _(), _(), _(322), _(300, 312)));
		defaultPoses.add(new Pose(_(), _(0,325), _(), _(), _(322), _(300, 312)));

		return defaultPoses;
	}
	
	private static final EulerAngle _(float x, float y, float z) {
		return new EulerAngle(Math.toRadians(x), Math.toRadians(y), Math.toRadians(z));
	}
	
	private static final EulerAngle _(float x, float y) {
		return new EulerAngle(Math.toRadians(x), Math.toRadians(y), Math.toRadians(0));
	}
	
	private static final EulerAngle _(float x) {
		return new EulerAngle(Math.toRadians(x), Math.toRadians(0), Math.toRadians(0));
	}
	
	private static final EulerAngle _() {
		return new EulerAngle(Math.toRadians(0), Math.toRadians(0), Math.toRadians(0));
	}
	
	private Map<BodyPart, EulerAngle> poses;
	private ArmorStand armorStand = null;
	
	public Pose(ArmorStand armorStand) {
		this.poses = new HashMap<BodyPart, EulerAngle>();
		this.poses.put(BodyPart.BODY, armorStand.getBodyPose());
		this.poses.put(BodyPart.HEAD, armorStand.getHeadPose());
		this.poses.put(BodyPart.LEFT_ARM, armorStand.getLeftArmPose());
		this.poses.put(BodyPart.LEFT_LEG, armorStand.getLeftLegPose());
		this.poses.put(BodyPart.RIGHT_ARM, armorStand.getRightArmPose());
		this.poses.put(BodyPart.RIGHT_LEG, armorStand.getRightLegPose());
		this.armorStand = armorStand;
	}
	
	public Pose(EulerAngle bodyAngle, EulerAngle headAngle, EulerAngle leftLegAngle, EulerAngle rightLegAngle, EulerAngle leftArmAngle, EulerAngle rightArmAngle) {
		this.poses = new HashMap<BodyPart, EulerAngle>();
		this.poses.put(BodyPart.BODY, bodyAngle);
		this.poses.put(BodyPart.HEAD, headAngle);
		this.poses.put(BodyPart.LEFT_ARM, leftArmAngle);
		this.poses.put(BodyPart.LEFT_LEG, leftLegAngle);
		this.poses.put(BodyPart.RIGHT_ARM, rightArmAngle);
		this.poses.put(BodyPart.RIGHT_LEG, rightLegAngle);
	}
	
	public Pose(double[] bodyAngle, double[] headAngle, double[] leftArmAngle, double[] leftLegAngle, double[] rightArmAngle, double[] rightLegAngle) {
		this.poses = new HashMap<BodyPart, EulerAngle>();
		this.poses.put(BodyPart.BODY, new EulerAngle(bodyAngle[0], bodyAngle[1], bodyAngle[2]));
		this.poses.put(BodyPart.HEAD, new EulerAngle(headAngle[0], headAngle[1], headAngle[2]));
		this.poses.put(BodyPart.LEFT_ARM, new EulerAngle(leftArmAngle[0], leftArmAngle[1], leftArmAngle[2]));
		this.poses.put(BodyPart.LEFT_LEG, new EulerAngle(leftLegAngle[0], leftLegAngle[1], leftLegAngle[2]));
		this.poses.put(BodyPart.RIGHT_ARM, new EulerAngle(rightArmAngle[0], rightArmAngle[1], rightArmAngle[2]));
		this.poses.put(BodyPart.RIGHT_LEG, new EulerAngle(rightLegAngle[0], rightLegAngle[1], rightLegAngle[2]));
	}
	
	public Map<BodyPart, EulerAngle> toMap() {
		return this.poses;
	}
	
	public Pose next() {
		int index = this.getIndex();
		List<Pose> defaultPoses = Pose.getDefault();
		if(index < 0) {
			index = -1;
		}
		if(index >= defaultPoses.size() - 1) {
			return defaultPoses.get(0);
		}
		return Pose.getDefault().get(index + 1);
	}
	

	
	public EulerAngle get(BodyPart bodyPart) {
		return this.poses.get(bodyPart);
	}
	
	public void setPose(BodyPart bodyPart, EulerAngle eulerAngle) {
		this.poses.put(bodyPart, eulerAngle);
	}
	
	public void apply() {
		if(this.armorStand == null) {
			Core.logger.warning("Pose#apply() has been called without constructing an armor stand");
			return;
		}
		this.armorStand.setBodyPose(this.poses.get(BodyPart.BODY));
		this.armorStand.setHeadPose(this.poses.get(BodyPart.HEAD));
		this.armorStand.setLeftArmPose(this.poses.get(BodyPart.LEFT_ARM));
		this.armorStand.setLeftLegPose(this.poses.get(BodyPart.LEFT_LEG));
		this.armorStand.setRightArmPose(this.poses.get(BodyPart.RIGHT_ARM));
		this.armorStand.setRightLegPose(this.poses.get(BodyPart.RIGHT_LEG));
	}
	
	public void applyTo(ArmorStand armorStand) {
		armorStand.setBodyPose(this.poses.get(BodyPart.BODY));
		armorStand.setHeadPose(this.poses.get(BodyPart.HEAD));
		armorStand.setLeftArmPose(this.poses.get(BodyPart.LEFT_ARM));
		armorStand.setLeftLegPose(this.poses.get(BodyPart.LEFT_LEG));
		armorStand.setRightArmPose(this.poses.get(BodyPart.RIGHT_ARM));
		armorStand.setRightLegPose(this.poses.get(BodyPart.RIGHT_LEG));
	}
	
	public boolean isDefault() {
		return this.getIndex() > 0;
	}
	
	public int getIndex() {
		Map<BodyPart, EulerAngle> poses = this.toMap();
		List<Pose> defaultPoses = Pose.getDefault();
		int iteration = 0;
		for(Pose defaultPose : defaultPoses) {
			boolean isCorrect = true;
			Map<BodyPart, EulerAngle> defaultMap = defaultPose.toMap();
			for(Entry entry : defaultMap.entrySet()) {
				EulerAngle defaultAngle = (EulerAngle) entry.getValue();
				EulerAngle selfAngle = (EulerAngle) poses.get(entry.getKey());
				if(!defaultAngle.equals(selfAngle)) {
					isCorrect = false;
					break;
				}
			}
			if(isCorrect) return iteration;
			iteration++;
		}
		return -1;
	}
	
}
