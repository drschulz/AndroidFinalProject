/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.dsaw.hophome;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

public class GameObject {
	public final Vector3 position;
	public final BoundingBox bound;
	public final Rectangle bounds;
	public final float width;
	public final float height;
	public final float depth;

	public GameObject (float x, float y, float z, float width, float height, float depth) {
		this.position = new Vector3(x, y, z);
		Vector3 near = new Vector3(x-width/2.0f, y-height/2.0f, z+depth/2.0f);
		Vector3 far = new Vector3(x+width/2.0f, y+height/2.0f, z-depth/2.0f);
		this.bound = new BoundingBox(near, far);
		this.bounds = new Rectangle(x-width/2, y-height/2, width, height);
		this.width = width;
		this.height = height;
		this.depth = depth;
	}
	
	protected void updateBound() {
		Vector3 near = new Vector3(position.x-width/2.0f, position.y-height/2.0f, position.z+depth/2.0f);
		Vector3 far = new Vector3(position.x+width/2.0f, position.y+height/2.0f, position.z-depth/2.0f);
		this.bound.set(near, far);
	}
}
