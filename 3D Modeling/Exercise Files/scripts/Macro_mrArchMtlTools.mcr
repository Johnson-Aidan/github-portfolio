--***********************************************************************************************
--
--  Created:  June 13 2006
--  Author:   PF Breton, Zap Andersson
--  Arch&Design AO/RC Option and conversion tool
--
--***********************************************************************************************


--***********************************************************************************************
-- MODIFY THIS AT YOUR OWN RISK
-- Additional Modifications:
--

macroScript mrArchMtlTools
enabledIn:#("max") --pfb: 2003.12.11 added product switch
category:"mental ray"
internalcategory:"mental ray"
ButtonText:"mr Arch & Design Tools"
Tooltip:"mr Arch & Design Tools" 
(--macro begins

    local i
    local newmtl

(

--- Is This Really True!?! ;-)

fn isTrue theValue =
(
    if classof theValue == BooleanClass then
        theValue
    else     if superclassof theValue == Number     then
        theValue != 0.0
    else     
        false    
)

--------------------------------------------------------------
---  Convert Material Function
---------------------------------------------------------------
fn convert_Mtl_to_mrArchitectural mtl=
    (	
        -- default to no change
        newmtl = mtl
        
        case classof mtl of
        (
            
            ------------------------------------------------------------------------------------------------------------------
            --Vray Materials
            ------------------------------------------------------------------------------------------------------------------
            vRayMtl:
            (			
                -- do the job
                newmtl = mrArchMaterial ()
                -- keep the name
                newmtl.name = mtl.name
                
                newmtl.diff_color = mtl.Diffuse
                if (mtl.texmap_diffuse != undefined AND mtl.texmap_diffuse_on) then 
                (				
                    newmtl.diff_color_map = mtl.texmap_diffuse
                    newmtl.diff_weight    = mtl.texmap_diffuse_multiplier / 100
                )
                else
                    newmtl.diff_weight = 1.0 -- vRay has no diffuse level					
                
                newmtl.refl_color = mtl.Reflection
                if (mtl.texmap_reflection != undefined AND mtl.texmap_reflection_on) then 
                (
                    newmtl.refl_color_map = mtl.texmap_reflection
                    newmtl.refl_weight    = mtl.texmap_reflection_multiplier / 100
                )
                else
                    newmtl.refl_weight = 1.0 -- vRay has no reflective level					
                
                newmtl.refl_gloss = mtl.reflection_glossiness
                newmtl.refl_gloss_map = mtl.texmap_reflectionGlossiness
                newmtl.refl_gloss_map_on = mtl.texmap_reflectionGlossiness_on				
                newmtl.refl_samples = mtl.reflection_subdivs
                
                newmtl.refl_func_fresnel = isTrue mtl.reflection_fresnel
                
                if (not newmtl.refl_func_fresnel) do
                (
                    -- somewhat arbitrary conversion for refl_func_low
                    newmtl.refl_func_low = 1.0 - ((mtl.Diffuse.r + mtl.Diffuse.g + mtl.Diffuse.b) / 4.0)
                    
                    if (newmtl.refl_func_low < 0.2) then newmtl.refl_func_low = 0.2
                )
                            
                newmtl.refr_color     = mtl.Refraction
                
                if (mtl.texmap_refraction != undefined AND mtl.texmap_refraction_on) then 
                (
                    newmtl.refr_color_map = mtl.texmap_refraction
                    newmtl.refr_weight    = mtl.texmap_refraction_multiplier / 100
                )
                else
                    newmtl.refr_weight = 1.0 -- vRay has no refraction level
                    
                newmtl.refr_gloss        = mtl.refraction_glossiness
                newmtl.refr_gloss_map    = mtl.texmap_refractionGlossiness
                newmtl.refr_gloss_map_on = mtl.texmap_refractionGlossiness_on
                
                newmtl.refr_samples   = mtl.refraction_subdivs
                newmtl.refr_ior       = mtl.refraction_ior
                newmtl.refr_ior_map   = mtl.texmap_refractionIOR
                
                if (not mtl.reflection_lockIOR) do
                (
                    -- turn fresnel func off
                    newmtl.refl_func_fresnel = false
                    -- approximate separate reflection IOR with "func low"
                    newmtl.refl_func_low = mtl.reflection_ior / 20
                    -- make sure below 1.0
                    if (newmtl.refl_func_low > 1.0) then newmtl.refl_func_low = 1.0
                )
                
                newmtl.refr_trans_on      = isTrue mtl.translucency_on
                newmtl.refr_transc        = mtl.translucency_color
                newmtl.refr_transc_map    = mtl.texmap_translucent
                newmtl.refr_transc_map_on = isTrue mtl.texmap_translucent_on
                
                newmtl.anisotropy    = 1.0 - mtl.anisotropy
                newmtl.anisoangle    = mtl.anisotropy_rotation / 360
                
                newmtl.aniso_mode    = mtl.anisotropy_derivation
                newmtl.aniso_channel = mtl.anisotropy_channel
                
                newmtl.refl_hlonly   = not mtl.option_traceReflection
                newmtl.opts_1sided   = not mtl.option_doubleSided				
                newmtl.opts_skip_inside = not mtl.option_reflectOnBack

                newmtl.refl_cutoff = mtl.option_cutOff
                newmtl.refr_cutoff = mtl.option_cutOff
    
                -- unclear if this can be translated, so the value
                -- is copied but distance falloff is not turned ON...
                newmtl.refr_falloff_color = mtl.refraction_fogColor
                newmtl.refr_falloff_dist  = mtl.translucency_thickness

                newmtl.bump_map     = mtl.texmap_bump
                newmtl.bump_map_amt = mtl.texmap_bump_multiplier / 100
                newmtl.bump_map_on  = mtl.texmap_bump_on
                

                newmtl.displacement_map     = mtl.texmap_displacement
                newmtl.displacement_map_amt = mtl.texmap_displacement_multiplier / 100
                newmtl.displacement_map_on  = mtl.texmap_displacement_on
                
                -- These aren't exact matches but better than nothing...
                newmtl.cutout_map        = mtl.texmap_opacity
                newmtl.cutout_map_on     = mtl.texmap_opacity_on
                newmtl.environment_map   = mtl.texmap_environment
                newmtl.environment_map_on= mtl.texmap_environment_on
            )--end VrayMtl

            vRayMtlWrapper: 
            (
                convert_Mtl_to_mrArchitectural mtl.BaseMtl				
            )
            
            ------------------------------------------------------------------------------------------------------------------
            --mi DGS phenomenon
            ------------------------------------------------------------------------------------------------------------------

            DGS_Material__physics_phen:
            (
                -- do the job
                newmtl = mrArchMaterial ()
                -- keep the name
                newmtl.name = mtl.name
                
                -------------------------------------
                --- diffuse
                newmtl.diff_color = mtl.diffuse			
                newmtl.diff_color_map = mtl.'diffuse.shader'
                newmtl.diff_color_map_on = mtl.'diffuse.connected'
                newmtl.diff_color_map = mtl.'diffuse.shader'				
                newmtl.diff_weight = 1.0
                
                -------------------------------------
                --- shiny
                -------------------------------------
                newmtl.refl_color = mtl.glossy
                newmtl.refl_color_map = mtl.'glossy.shader'
                newmtl.refl_color_map_on = mtl.'glossy.connected'
                newmtl.refl_weight = 1.0	

                -------------------------------------
                --- glossy
                -------------------------------------
                newmtl.refl_gloss = mtl.shiny / 10 --guess here
                newmtl.refl_gloss_map = mtl.'shiny.shader'
                newmtl.refl_gloss_map_on = mtl.'shiny.connected'

                -------------------------------------
                --- ior
                -------------------------------------
                newmtl.refl_func_fresnel = true			
                newmtl.refr_ior = mtl.ior
                newmtl.refr_ior_map = mtl.'ior.shader'
                newmtl.refr_ior_map_on = mtl.'ior.connected'

                -------------------------------------
                --- transparency
                -------------------------------------
                newmtl.refr_color_map = mtl.'transp.shader'
                newmtl.refr_color_map_on = mtl.'transp.connected'
                newmtl.refr_weight = mtl.transp

                
            )
            ------------------------------------------------------------------------------------------------------------------
            --3ds max Architectural Materials
            ------------------------------------------------------------------------------------------------------------------
            ArchitecturalMaterial: 
            (		
                -- do the job
                newmtl = mrArchMaterial ()
                -- keep the name
                newmtl.name = mtl.name
                newmtl.opts_backface_cull = not mtl.twosided 
                
                -------------------------------------
                --- diffuse
                newmtl.diff_color = mtl.Diffuse			
                newmtl.diff_color_map = mtl.maps[1]
                newmtl.diff_color_map_on = mtl.mapEnables[1]
                newmtl.diff_weight    = mtl.mapAmounts[1] / 100
                
                
                -------------------------------------
                --- reflectivity
                newmtl.refl_color = mtl.Diffuse
                newmtl.refl_color_map = mtl.maps[2]
                newmtl.refl_color_map_on = mtl.mapEnables[2]
                newmtl.refl_weight = mtl.mapAmounts[2] / 100	
                newmtl.refl_gloss = 1.0			
                
                -------------------------------------
                --- ior	
                newmtl.refl_func_fresnel = true			
                newmtl.refr_ior       = mtl.ior

                -------------------------------------
                --- transparency				
                newmtl.refr_color   =  mtl.Diffuse
                newmtl.refr_color_map = mtl.maps[4]
                newmtl.refr_color_map_on = mtl.mapEnables[4]
                newmtl.refr_weight    = mtl.mapAmounts[4] / 100 
            

                -------------------------------------
                --- transluscency				
                newmtl.refr_transc   =  mtl.Diffuse
                newmtl.refr_trans_on = mtl.mapEnables[7]
                newmtl.refr_transc_map = mtl.maps[7]
                newmtl.refr_transw = mtl.mapAmounts[7] / 100

                -------------------------------------
                --- luminance note: not yet available in the mr arch mat
                /*
                    
                    newmtl.luminance_map_on = mtl.mapEnables[3]
                    newmtl.luminance_map = mtl.maps[3]
                */

                -------------------------------------
                --- bump				
                
                newmtl.bump_map_on = mtl.mapEnables[5]
                newmtl.bump_map = mtl.maps[5]
                newmtl.bump_map_amt = mtl.mapAmounts[5] / 100
                
                -------------------------------------
                --- displacement				
                
                newmtl.displacement_map_on = mtl.mapEnables[6]
                newmtl.displacement_map = mtl.maps[6]
                newmtl.displacement_map_amt = mtl.mapAmounts[6] / 100
                
                -------------------------------------
                --- cutout		
            
                newmtl.cutout_map_on = mtl.mapEnables[8]
                newmtl.cutout_map = mtl.maps[8]
                        
            )--end ArchitecturalMaterial
            

            ------------------------------------------------------------------------------------------------------------------
            --Standard Materials
            ------------------------------------------------------------------------------------------------------------------
            standardMaterial:
            (				
                -- do the job
                newmtl = mrArchMaterial ()
                -- keep the name
                newmtl.name = mtl.name
                newmtl.opts_backface_cull = not mtl.twosided 
                
                -------------------------------------
                --- diffuse
                newmtl.diff_color = mtl.diffuse				
                newmtl.diff_color_map = mtl.maps[2]
                newmtl.diff_weight    = mtl.mapAmounts[2] / 100
                newmtl.diff_color_map_on = mtl.mapEnables[2]
        
                
                -------------------------------------
                --- specular color
                newmtl.refl_color = mtl.specular
                newmtl.refl_color_map = mtl.maps[3]
                newmtl.refl_color_map_on = mtl.mapEnables[3]
                newmtl.refl_weight = mtl.mapAmounts[3] / 100
                    
                -------------------------------------
                --- specular level
                newmtl.refl_weight = mtl.specularLevel /100

                -------------------------------------
                --- glossiness
                newmtl.refl_gloss = mtl.glossiness /100
                newmtl.refl_gloss_map = mtl.maps[5]	
                newmtl.refl_gloss_map_on = mtl.mapEnables[5]

                -------------------------------------
                --- bump
                newmtl.bump_map = mtl.maps[9]	
                newmtl.bump_map_on = mtl.mapEnables[9]								
                newmtl.bump_map_amt = mtl.mapAmounts[9] /100							
        

                -------------------------------------
                --- displacement				
                newmtl.displacement_map_on = mtl.mapEnables[12]
                newmtl.displacement_map = mtl.maps[12]
                newmtl.displacement_map_amt = mtl.mapAmounts[12] / 100

            )--end Standard Material
                    

            ------------------------------------------------------------------------------------------------------------------
            -- Raytraced Material
            ------------------------------------------------------------------------------------------------------------------			
            RaytraceMaterial: return()

            ------------------------------------------------------------------------------------------------------------------
            -- Multimaterials
            ------------------------------------------------------------------------------------------------------------------
            MultiMaterial: 
                (
                    -- function call itself to harvest the materials inside the MultiMaterial
                    local u
                    orgMtl = mtl
                    
                    for u = 1 to mtl.numsubs do 
                    (
                        convert_Mtl_to_mrArchitectural mtl[u] 
                        mtl[u] = newmtl
                    )					
                    
                    -- set so parent knows what to set...
                    newmtl = mtl					
                )

            ------------------------------------------------------------------------------------------------------------------
            -- Lightscape Materials
            ------------------------------------------------------------------------------------------------------------------
            Lightscape_Mtl: 
                (					
                )
            

            Blend: 
                (
                    convert_Mtl_to_mrArchitectural mtl.map1
                    mtl.map1 = newmtl
                    convert_Mtl_to_mrArchitectural mtl.map2					
                    mtl.map2 = newmtl					
                    -- set so parent knows what to set...
                    newmtl = mtl
                )

            -- These should be handled sorta like Blend above...			
            CompositeMaterial: return()			
            DoubleSided: return()
            Ink__n_Paint: return()
            MatteShadow: return()
            MorpherMaterial: return()
            MtlEval_Test_Mtl: return()
            Shellac: return()
            TopBottom: return()
            default: return()
        )	 
    ) -- end fn convert material function 



----------------------------------------------------------------
--  Set Parameter Function
-----------------------------------------------------------------
    fn setMtlParam mtl param val =
    (	
    
        case classof mtl of
        (
            
            ------------------------------------------------------------------------------------------------------------------
            --mrArchMtl
            ------------------------------------------------------------------------------------------------------------------
            mrArchMaterial:
            (	
                SetProperty mtl param val
            )
        
            ------------------------------------------------------------------------------------------------------------------
            -- Multimaterials
            -- function call itself to harvest the materials inside the MultiMaterial
            ------------------------------------------------------------------------------------------------------------------
            MultiMaterial: 
                (
                    
                    local u
                    orgMtl = mtl
                    
                    for u = 1 to mtl.numsubs do 
                    (
                        setMtlParam mtl[u] param val
                    )					
                                    
                )
        )--end case classof
    )--end fn




rollout mrArchMtlTools "Affect Materials..." width:250 height:100
(
    local mtls = sceneMaterials
    local sel
    
    radiobuttons only_medit labels:#("...in scene", "...in material editor only")
    
    on only_medit changed sel do (
        mtls = sceneMaterials
        
        if sel == 2 do (
            mtls = meditMaterials
        )
    )
)

rollout mrArchMtlAOTools "Ambient Occlusion" width:250 height:100
(
    local mtl
    
    button  aoOn  "Turn AO ON in all Arch & Design Materials" pos:[10,10] width:220
    button  aoOff "Turn AO OFF in all Arch & Design Materials" pos:[10,70] width:220


    button  setDistance "Force AO radius:" pos:[10,40] width:100
    spinner ao_distance "" range:[0,100000000000000,10] type:#worldunits pos:[120,42]


    -- get a default
    on mrArchMtlAOTools open do (
        for mtl in mrArchMtlTools.mtls do 
        (
            if (classof mtl) == mrArchMaterial do 
            (
                ao_distance.value = mtl.opts_ao_distance
            )
        )	
    )--end pressed

    on setDistance pressed do (
        with undo on
        (
            for mtl in mrArchMtlTools.mtls do setMtlParam mtl #opts_ao_distance ao_distance.value
        )
    )--end pressed

    on aoOff pressed do (
        with undo on
        (	
            for mtl in mrArchMtlTools.mtls do setMtlParam mtl #opts_ao_on false
        )
    )--end pressed

    on aoOn pressed do (
    with undo on
        (
            for mtl in mrArchMtlTools.mtls do setMtlParam mtl #opts_ao_on true
        )
    )--end pressed

)--end rollout
rollout mrArchMtlRCTools "Round Corners" width:250 height:100
(
    local mtl
    
    

    -----------------------------------
    ---  UI
    -----------------------------------
    button  rcOn  "Turn Round Corners ON in all materials" pos:[10,10] width:220
    button  rcOff "Turn Round Corners OFF in all materials" pos:[10,70] width:220


    button  setDistance "Force RC radius:" pos:[10,40] width:100
    spinner rc_distance "" range:[0,100000000000000,0.25] type:#worldunits pos:[120,42]

    -- Get a default
    on mrArchMtlRCTools open do (				
        mtl = sceneMaterials
        
        if mrArchMtlTools.only_medit == 1 do mtl = meditMaterials

        for mtl in mrArchMtlTools.mtls do 
        (
            if (classof mtl) == mrArchMaterial do 
            (
                rc_distance.value = mtl.opts_round_corners_radius
            )
        )	
    )--end on open

    
    --perform changes in the materials
    on setDistance pressed do (
        
        with undo on
        (
            for mtl in mrArchMtlTools.mtls do setMtlParam mtl #opts_round_corners_radius rc_distance.value
        )
    )--end pressed

    on rcOff pressed do (
        with undo on
        (
            for mtl in mrArchMtlTools.mtls do setMtlParam mtl #opts_round_corners_on false
        )
    )--end pressed

    on rcOn pressed do (
        with undo on
        (
            for mtl in mrArchMtlTools.mtls do setMtlParam mtl #opts_round_corners_on true
        )
    )--end on pressed
)



rollout mrArchMtlConvertTools "Convert Materials" width:250 height:100
(
    local mtl
    

    -----------------------------------
    ---  UI
    -----------------------------------
    button  btnConvert  "Convert Materials to mr Arch & Design" pos:[10,10] width:220
    
        
    --perform changes in the materials
    on btnConvert  pressed do (
        
        with undo on
        (
            disableSceneRedraw()
            setWaitCursor()
            
            local orgSceneMaterials = mrArchMtlTools.mtls
        
            for i = 1 to orgSceneMaterials.count do 
            (
                local u
                    
                convert_Mtl_to_mrArchitectural orgSceneMaterials[i]
                print ("converted " + orgSceneMaterials[i].name)
                for u = 1 to objects.count do
                (
                    if (objects[u].material == orgSceneMaterials[i]) do
                    (
                        objects[u].material = newmtl
                    )
                )
                
            )--end for
            
        
            setArrowCursor()
            enableSceneRedraw()
            completeredraw()
    
        )
    )--end pressed

)--end rollout




)--end script


on execute do	
(
   

        -- create the rollout window and add the  rollout
        if mrArchMtlAOToolsFloater != undefined do	closerolloutfloater mrArchMtlAOToolsFloater
        mrArchMtlAOToolsFloater = newRolloutFloater "mrArch&Design Utilities" 250 360
        addRollout mrArchMtlTools mrArchMtlAOToolsFloater
        addRollout mrArchMtlAOTools mrArchMtlAOToolsFloater
        addRollout mrArchMtlRCTools mrArchMtlAOToolsFloater
        addRollout mrArchMtlConvertTools mrArchMtlAOToolsFloater
)


)--end macro




