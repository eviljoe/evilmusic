/*
 * EvilMusic - Web-Based Music Player Copyright (C) 2015 Joe Falascino
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */

package em.model;

/**
 * @since v0.1
 * @author eviljoe
 */
public enum EMVersion {
    
    /** v0.1 */
    V0_1(0, "v0.1");
    
    private final int order;
    private final String pretty;
    
    private EMVersion(int order, String pretty) {
        this.order = order;
        this.pretty = pretty;
    }
    
    public int getOrder() {
        return order;
    }
    
    @Override
    public String toString() {
        return pretty;
    }
}
