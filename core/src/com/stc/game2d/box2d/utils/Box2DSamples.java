package com.stc.game2d.box2d.utils;

import com.stc.game2d.box2d.BouncingBallSample;
import com.stc.game2d.box2d.BuoyancySample;
import com.stc.game2d.box2d.CollisionsSample;
import com.stc.game2d.box2d.DragAndDropSample;
import com.stc.game2d.box2d.GravityAccelerometerSample;
import com.stc.game2d.box2d.ImpulsesSample;
import com.stc.game2d.box2d.JumpingSample;
import com.stc.game2d.box2d.SpawnBodiesSample;
import com.stc.game2d.box2d.SpritesSample;
import com.stc.game2d.utils.Sample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Box2DSamples {

	@SuppressWarnings("unchecked")
	public static final List<Class<? extends Sample>> SAMPLES = new ArrayList<Class<? extends Sample>>(
			Arrays.asList(
					BouncingBallSample.class,
					SpawnBodiesSample.class,
					DragAndDropSample.class,
					ImpulsesSample.class,
					SpritesSample.class,
					GravityAccelerometerSample.class,
					CollisionsSample.class,
					BuoyancySample.class,
					JumpingSample.class
			)
	);

}
